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
import org.springblade.core.secure.utils.SecureUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.management.entity.Comprehensive;
import org.springblade.management.vo.ComprehensiveVO;
import org.springblade.management.wrapper.ComprehensiveWrapper;
import org.springblade.management.service.IComprehensiveService;
import org.springblade.core.boot.ctrl.BladeController;
import java.util.List;

/**
 *  控制器
 *
 * @author Blade
 * @since 2021-01-31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/comprehensive")
@Api(value = "", tags = "接口")
public class ComprehensiveController extends BladeController {

	private IComprehensiveService comprehensiveService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入comprehensive")
	public R<ComprehensiveVO> detail(Comprehensive comprehensive) {
		Comprehensive detail = comprehensiveService.getOne(Condition.getQueryWrapper(comprehensive));
		return R.data(ComprehensiveWrapper.build().entityVO(detail));
	}

	/**
	* 分页
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入comprehensive")
	public R<IPage<ComprehensiveVO>> list(Comprehensive comprehensive, Query query) {
		String userRole = SecureUtil.getUserRole();
		if(userRole.equals("staff")){
			Long userId = SecureUtil.getUserId();
			comprehensive.setStaffId(userId);
		}
		IPage<Comprehensive> pages = comprehensiveService.page(Condition.getPage(query), Condition.getQueryWrapper(comprehensive));
		return R.data(ComprehensiveWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入comprehensive")
	public R<IPage<ComprehensiveVO>> page(ComprehensiveVO comprehensive, Query query) {
		IPage<ComprehensiveVO> pages = comprehensiveService.selectComprehensivePage(Condition.getPage(query), comprehensive);
		return R.data(pages);
	}

	/**
	* 新增
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入comprehensive")
	public R save(@Valid @RequestBody Comprehensive comprehensive) {
		return R.status(comprehensiveService.save(comprehensive));
	}

	/**
	* 修改
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入comprehensive")
	public R update(@Valid @RequestBody Comprehensive comprehensive) {
		return R.status(comprehensiveService.updateById(comprehensive));
	}

	/**
	* 新增或修改
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入comprehensive")
	public R submit(@Valid @RequestBody Comprehensive comprehensive) {
		return R.status(comprehensiveService.generateSalary());
	}


	/**
	* 删除
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(comprehensiveService.deleteLogic(Func.toLongList(ids)));
	}


}
