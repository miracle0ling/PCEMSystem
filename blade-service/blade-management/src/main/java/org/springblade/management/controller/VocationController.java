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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import org.springblade.management.service.IStaffService;
import org.springblade.system.user.feign.IUserClient;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.management.entity.Vocation;
import org.springblade.management.vo.VocationVO;
import org.springblade.management.wrapper.VocationWrapper;
import org.springblade.management.service.IVocationService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.ArrayList;
import java.util.List;

/**
 *  控制器
 *
 * @author Blade
 * @since 2021-01-08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vocation")
@Api(value = "", tags = "接口")
public class VocationController extends BladeController {

	private IVocationService vocationService;
	private IStaffService staffService;


	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入vocation")
	public R<VocationVO> detail(Vocation vocation) {
		Vocation detail = vocationService.getOne(Condition.getQueryWrapper(vocation));
		return R.data(VocationWrapper.build().entityVO(detail));
	}

	/**
	* 分页
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入vocation")
	public R<IPage<VocationVO>> list(Vocation vocation, Query query) {

		String userRole = SecureUtil.getUserRole();
		if(userRole.equals("staff")){
			Long userId = SecureUtil.getUserId();
			vocation.setStaffId(userId);
		}

		Integer size = query.getSize();
		Integer current = query.getCurrent();

		List<Vocation> vocations = vocationService.selectAll(vocation);
		IPage<Vocation> pages = new Page<>();
		pages.setPages(vocations.size()/size);
		pages.setTotal(vocations.size());
		pages.setCurrent(current);

		current = (current - 1) * size;
		List<Vocation> result = new ArrayList<>();
		if (size + current < vocations.size()) {
			result = vocations.subList(current, current + size);
		} else {
			result = vocations.subList(current, vocations.size());
		}

		pages.setRecords(result);

		return R.data(VocationWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入vocation")
	public R<IPage<VocationVO>> page(VocationVO vocation, Query query) {
		IPage<VocationVO> pages = vocationService.selectVocationPage(Condition.getPage(query), vocation);
		return R.data(pages);
	}

	/**
	* 新增
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入vocation")
	public R save(@Valid @RequestBody Vocation vocation) {
		return R.status(vocationService.save(vocation));
	}

	/**
	* 修改
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入vocation")
	public R update(@Valid @RequestBody Vocation vocation) {
		return R.status(vocationService.updateById(vocation));
	}

	/**
	* 新增或修改
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入vocation")
	public R submit(@Valid @RequestBody Vocation vocation) {
		if (vocation.getStaffId()==null){
			Long userId = SecureUtil.getUserId();
			vocation.setStaffId(userId);
		}
		return R.status(vocationService.saveOrUpdate(vocation));
	}


	/**
	* 删除
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(vocationService.removeByIds(Func.toLongList(ids)));
	}


	/**
	 * 审批
	 */
	@PostMapping("/review")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R review(@ApiParam(value = "主键集合", required = true) @RequestParam String ids,@RequestParam Integer type) {
		return R.status(vocationService.reviewByIds(Func.toLongList(ids),type));
	}


}
