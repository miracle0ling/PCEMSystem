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
package org.springblade.management.mapper;

import feign.Param;
import org.mapstruct.Mapper;
import org.springblade.management.entity.Vocation;
import org.springblade.management.vo.VocationVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Blade
 * @since 2021-01-08
 */
@Mapper
public interface VocationMapper extends BaseMapper<Vocation> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param vocation
	 * @return
	 */
	List<VocationVO> selectVocationPage(IPage page, VocationVO vocation);

	/**
	 * 通过id集合查找
	 *
	 * @param ids
	 * @param type
	 * @return
	 */
	Integer reviewByIds(@Param("ids") List<Long> ids, @Param("type") Integer type);


	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param vocation
	 * @return
	 */
	List<Vocation> selectAll(Vocation vocation);

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param vocation
	 * @return
	 */
	Vocation getMonthById(Long id);

	/**
	 * 通过id集合查找
	 * @param vocation
	 * @return
	 */
	List<Vocation> selectByIds(@Param("ids") List<Long> ids);

}
