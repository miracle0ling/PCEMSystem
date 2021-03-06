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
package org.springblade.management.service;

import org.springblade.management.entity.Staff;
import org.springblade.management.vo.StaffVO;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author Blade
 * @since 2021-01-08
 */
public interface IStaffService extends BaseService<Staff> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param staff
	 * @return
	 */
	IPage<StaffVO> selectStaffPage(IPage<StaffVO> page, StaffVO staff);

	/**
	 * 新增数据
	 *
	 * @param page
	 * @param staff
	 * @return
	 */
	Boolean addNew(Staff newStaff);

	/**
	 * 通过id查找员工信息
	 *
	 * @param page
	 * @param staff
	 * @return
	 */
	Staff selectById(Long staffid);

}
