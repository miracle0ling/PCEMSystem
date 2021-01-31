/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.management.service.impl;

import org.springblade.management.entity.Achievements;
import org.springblade.management.entity.Comprehensive;
import org.springblade.management.entity.Staff;
import org.springblade.management.entity.Vocation;
import org.springblade.management.mapper.AchievementsMapper;
import org.springblade.management.mapper.StaffMapper;
import org.springblade.management.mapper.VocationMapper;
import org.springblade.management.service.IStaffService;
import org.springblade.management.vo.ComprehensiveVO;
import org.springblade.management.mapper.ComprehensiveMapper;
import org.springblade.management.service.IComprehensiveService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2021-01-31
 */
@Service
public class ComprehensiveServiceImpl extends BaseServiceImpl<ComprehensiveMapper, Comprehensive> implements IComprehensiveService {

	@Autowired
	private AchievementsMapper achievementsMapper;
	@Autowired
	private VocationMapper vocationMapper;
	@Autowired
	private StaffMapper staffMapper;
	@Autowired
	private ComprehensiveMapper comprehensiveMapper;
	@Autowired
	private IStaffService staffService;

	@Override
	public IPage<ComprehensiveVO> selectComprehensivePage(IPage<ComprehensiveVO> page, ComprehensiveVO comprehensive) {
		return page.setRecords(baseMapper.selectComprehensivePage(page, comprehensive));
	}

	@Override
	public Boolean generateSalary() {
		List<Comprehensive> result = new ArrayList<>();
		List<Achievements> month = achievementsMapper.getMonth();
		LocalDateTime time = LocalDateTime.of(LocalDate.from(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth())), LocalTime.MAX);
		try {
			for (Achievements i :
				month) {
				Comprehensive comprehensive = new Comprehensive();
				Staff staff = staffMapper.selectByStaffId(i.getStaffId());
				Vocation vocationMonth = vocationMapper.getMonthById(i.getStaffId());
				Integer overtimeLeave = staff.getOvertimeLeave();
				Integer days = new Integer(0);
				if (vocationMonth!=null){
					if (vocationMonth.getEndTime().isAfter(time)){
						days = (int) (Duration.between(vocationMonth.getBeginTime(), time).toDays());
						Integer overdays = (int)(Duration.between(vocationMonth.getEndTime(), time).toDays());
						if (staff.getOvertimeLeave()<0){
							days-=overtimeLeave;
							overtimeLeave = overdays;
						}else {
							if (days>overtimeLeave){
								days-=overtimeLeave;
								overtimeLeave = overdays;
							}else {
								overtimeLeave=overtimeLeave-(days-overdays);
							}
						}
					}else {
						days = (int) (Duration.between(vocationMonth.getBeginTime(), vocationMonth.getEndTime()).toDays());
						if (staff.getOvertimeLeave()<0){
							days-=overtimeLeave;
							overtimeLeave = (int) (Duration.between(vocationMonth.getEndTime(), time).toDays());
						}else {
							if (days>overtimeLeave){
								days-=overtimeLeave;
								overtimeLeave = 0;
							}else {
								overtimeLeave-=days;
							}
						}
					}
					staff.setOvertimeLeave(overtimeLeave);
					staffService.saveOrUpdate(staff);
				}
				BigDecimal pro = calculatePerfomance(i.getRating());
				BigDecimal baseSalary = staff.getBaseSalary();
	//			工作天数
				BigDecimal workdays = getCurrentMonthDay().subtract(BigDecimal.valueOf(days));
				BigDecimal salaryResult = baseSalary.divide(getCurrentMonthDay(), 2).multiply(workdays).add(pro.multiply(staff.getPerformanceSalary()));
				comprehensive.setStaffSalary(salaryResult);
				comprehensive.setStaffVocation(days);
				comprehensive.setStaffId(i.getStaffId());
				comprehensive.setAchievementsRating(i.getRating());
				comprehensive.setStaffName(i.getStaffName());
				System.out.println(comprehensive);
				saveOrUpdate(comprehensive);
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return true;
	}

	public BigDecimal getCurrentMonthDay() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return BigDecimal.valueOf(maxDate);
	}

	public BigDecimal calculatePerfomance(String rating){
		switch (rating){
			case "S":{
				return BigDecimal.valueOf(1.2);
			}
			case "A":{
				return BigDecimal.valueOf(1.1);
			}
			case "B":{
				return BigDecimal.valueOf(1);
			}
			case "C":{
				return BigDecimal.valueOf(0.9);
			}
			case "D":{
				return BigDecimal.valueOf(0.8);
			}
		}
		return BigDecimal.valueOf(1.0);
	}

}
