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
package org.springblade.management.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.management.entity.Staff;
import org.springblade.management.vo.StaffVO;
import org.springblade.management.wrapper.StaffWrapper;
import org.springblade.management.service.IStaffService;
import org.springblade.core.boot.ctrl.BladeController;
import java.util.List;

/**
 *  控制器
 *
 * @author Blade
 * @since 2021-01-08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/staff")
@Api(value = "", tags = "接口")
public class StaffController extends BladeController {

	private IStaffService staffService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入staff")
	public R<StaffVO> detail(Staff staff) {
		Staff detail = staffService.getOne(Condition.getQueryWrapper(staff));
		return R.data(StaffWrapper.build().entityVO(detail));
	}

	/**
	* 分页
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入staff")
	public R<IPage<StaffVO>> list(Staff staff, Query query) {
		String userRole = SecureUtil.getUserRole();
		if (userRole.equals("manager")){
			Long userId = SecureUtil.getUserId();
			Staff staffTemp = staffService.selectById(userId);
			staff.setDeptId(staffTemp.getDeptId());
		}
		IPage<Staff> pages = staffService.page(Condition.getPage(query), Condition.getQueryWrapper(staff));
		return R.data(StaffWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入staff")
	public R<IPage<StaffVO>> page(StaffVO staff, Query query) {
		IPage<StaffVO> pages = staffService.selectStaffPage(Condition.getPage(query), staff);
		return R.data(pages);
	}

	/**
	* 新增
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入staff")
	public R save(@Valid @RequestBody Staff staff) {
		return R.status(staffService.save(staff));
	}

	/**
	* 修改
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入staff")
	public R update(@Valid @RequestBody Staff staff) {
		return R.status(staffService.updateById(staff));
	}

	/**
	* 新增或修改
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入staff")
	public R submit(@Valid @RequestBody Staff staff) {
		return R.status(staffService.saveOrUpdate(staff));
	}


	/**
	* 删除
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(staffService.deleteLogic(Func.toLongList(ids)));
	}


}
