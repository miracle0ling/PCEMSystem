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
	public boolean generateSalary() {
		List<Comprehensive> result = new ArrayList<>();
		//本月的信息
		List<Achievements> month = achievementsMapper.getMonth();
		//本月最后一天
		LocalDateTime time = LocalDateTime.of(LocalDate.from(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth())), LocalTime.MAX);
		try {
			for (Achievements i : month) {
				//将状态改为已生成工资
				i.setStatus(1);
				achievementsMapper.updateById(i);

				Comprehensive comprehensive = new Comprehensive();
				Staff staff = staffMapper.selectByStaffId(i.getStaffId());
				//本月已处理的信息
//				Vocation vocationMonth = vocationMapper.getMonthById(i.getStaffId());

				Integer overtimeLeave = staff.getOvertimeLeave();
				BigDecimal pro = calculatePerfomance(i.getRating());
				BigDecimal baseSalary = staff.getBaseSalary();
				//			工作天数
				BigDecimal workdays;
				BigDecimal basicResult = baseSalary;
				Integer days = new Integer(0);
				if (overtimeLeave<0){
					days = Math.abs(overtimeLeave);
					staff.setOvertimeLeave(0);
					staffService.saveOrUpdate(staff);
					workdays = getCurrentMonthDay().subtract(BigDecimal.valueOf(days));
					basicResult = baseSalary.divide(getCurrentMonthDay(),2).multiply(workdays).add(pro.multiply(staff.getPerformanceSalary()));
				}else {
					basicResult = baseSalary.add(pro.multiply(staff.getPerformanceSalary()));
				}

				System.out.println(basicResult);

				BigDecimal salaryResult = basicResult;
				//个税
				if (basicResult.compareTo(BigDecimal.valueOf(85000)) > -1) {
					basicResult = basicResult.subtract(BigDecimal.valueOf(85000)).multiply(BigDecimal.valueOf(0.55)).add(BigDecimal.valueOf(64160));
				}else if (basicResult.compareTo(BigDecimal.valueOf(60000)) > -1){
					basicResult = basicResult.subtract(BigDecimal.valueOf(60000)).multiply(BigDecimal.valueOf(0.65)).add(BigDecimal.valueOf(47910));
				}else if (basicResult.compareTo(BigDecimal.valueOf(40000)) > -1){
					basicResult = basicResult.subtract(BigDecimal.valueOf(40000)).multiply(BigDecimal.valueOf(0.7)).add(BigDecimal.valueOf(33910));
				}else if (basicResult.compareTo(BigDecimal.valueOf(30000)) > -1){
					basicResult = basicResult.subtract(BigDecimal.valueOf(30000)).multiply(BigDecimal.valueOf(0.75)).add(BigDecimal.valueOf(26410));
				}else if (basicResult.compareTo(BigDecimal.valueOf(17000)) > -1){
					basicResult = basicResult.subtract(BigDecimal.valueOf(17000)).multiply(BigDecimal.valueOf(0.8)).add(BigDecimal.valueOf(16010));
				}else if (basicResult.compareTo(BigDecimal.valueOf(8000)) > -1){
					basicResult = basicResult.subtract(BigDecimal.valueOf(8000)).multiply(BigDecimal.valueOf(0.9)).add(BigDecimal.valueOf(7910));
				}else if (basicResult.compareTo(BigDecimal.valueOf(5000)) > -1){
					basicResult = basicResult.subtract(BigDecimal.valueOf(5000)).multiply(BigDecimal.valueOf(0.97)).add(BigDecimal.valueOf(5000));
				}

				System.out.println(basicResult);
				System.out.println(salaryResult);
				//公积金
				BigDecimal provident = salaryResult.multiply(staff.getProvidentFund());
				if (provident.compareTo(BigDecimal.valueOf(28000)) > -1){
					provident = BigDecimal.valueOf(28000);
				}
				System.out.println(provident);
				salaryResult = basicResult.subtract(provident);

				System.out.println(salaryResult);
				comprehensive.setStaffSalary(salaryResult);
				comprehensive.setStaffVocation(days);
				comprehensive.setStaffId(i.getStaffId());
				comprehensive.setAchievementsRating(i.getRating());
				comprehensive.setStaffName(i.getStaffName());
				comprehensive.setDeptId(i.getDeptId());
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
