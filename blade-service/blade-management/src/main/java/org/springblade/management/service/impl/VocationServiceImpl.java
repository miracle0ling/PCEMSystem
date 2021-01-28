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

import org.springblade.management.entity.Vocation;
import org.springblade.management.vo.VocationVO;
import org.springblade.management.mapper.VocationMapper;
import org.springblade.management.service.IVocationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2021-01-08
 */
@Service
public class VocationServiceImpl extends ServiceImpl<VocationMapper, Vocation> implements IVocationService {

	@Autowired
	private VocationMapper vocationMapper;

	@Override
	public IPage<VocationVO> selectVocationPage(IPage<VocationVO> page, VocationVO vocation) {
		return page.setRecords(baseMapper.selectVocationPage(page, vocation));
	}

	@Override
	public Boolean reviewByIds(List<Long> ids, Integer type) {
		Integer num = vocationMapper.reviewByIds(ids, type);
		if (num>=1){
			return true;
		}
		return false;
	}

	@Override
	public List<Vocation> selectAll(Vocation vocation) {
		return vocationMapper.selectAll(vocation);
	}

}
