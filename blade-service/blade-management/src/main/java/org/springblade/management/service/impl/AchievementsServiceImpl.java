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
import org.springblade.management.vo.AchievementsVO;
import org.springblade.management.mapper.AchievementsMapper;
import org.springblade.management.service.IAchievementsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2021-01-29
 */
@Service
public class AchievementsServiceImpl extends ServiceImpl<AchievementsMapper, Achievements> implements IAchievementsService {

	@Autowired
	private AchievementsMapper achievementsMapper;

	@Override
	public IPage<AchievementsVO> selectAchievementsPage(IPage<AchievementsVO> page, AchievementsVO achievements) {
		return page.setRecords(baseMapper.selectAchievementsPage(page, achievements));
	}

	@Override
	public List<Achievements> selectAll(Achievements achievements) {
		return achievementsMapper.selectAll(achievements);
	}

}
