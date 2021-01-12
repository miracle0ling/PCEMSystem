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

import org.springblade.management.entity.Comprehensive;
import org.springblade.management.vo.ComprehensiveVO;
import org.springblade.management.mapper.ComprehensiveMapper;
import org.springblade.management.service.IComprehensiveService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2021-01-08
 */
@Service
public class ComprehensiveServiceImpl extends BaseServiceImpl<ComprehensiveMapper, Comprehensive> implements IComprehensiveService {

	@Override
	public IPage<ComprehensiveVO> selectComprehensivePage(IPage<ComprehensiveVO> page, ComprehensiveVO comprehensive) {
		return page.setRecords(baseMapper.selectComprehensivePage(page, comprehensive));
	}

}
