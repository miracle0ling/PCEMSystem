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
package org.springblade.management.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author Blade
 * @since 2021-01-08
 */
@Data
@TableName("blade_achievements")
@ApiModel(value = "Achievements对象", description = "Achievements对象")
public class Achievements implements Serializable {

    private static final long serialVersionUID = 1L;

  private Long id;
    /**
     * 项目数量
     */
    @ApiModelProperty(value = "项目数量")
    private Integer projectAmount;
    /**
     * 项目完成度
     */
    @ApiModelProperty(value = "项目完成度")
    private Integer projectCompletion;
    /**
     * 项目缺陷度
     */
    @ApiModelProperty(value = "项目缺陷度")
    private Integer projectDefect;
    /**
     * 项目修改完成度
     */
    @ApiModelProperty(value = "项目修改完成度")
    private Integer projectModify;
    /**
     * 项目计划完成度
     */
    @ApiModelProperty(value = "项目计划完成度")
    private Integer projectPlan;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 员工id
     */
    @ApiModelProperty(value = "员工id")
    private Long staffId;
    /**
     * 员工姓名
     */
    @ApiModelProperty(value = "员工姓名")
    private String staffName;


}
