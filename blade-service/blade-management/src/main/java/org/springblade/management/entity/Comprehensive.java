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

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import org.springblade.core.mp.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 *
 * @author Blade
 * @since 2021-01-31
 */
@Data
@TableName("blade_comprehensive")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Comprehensive对象", description = "Comprehensive对象")
public class Comprehensive extends BaseEntity {

    private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;
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
    /**
     * 员工请假加班时间
     */
    @ApiModelProperty(value = "员工请假加班时间")
    private Integer staffVocation;
    /**
     * 员工应发工资
     */
    @ApiModelProperty(value = "员工应发工资")
    private BigDecimal staffSalary;
    /**
     * 绩效评级
     */
    @ApiModelProperty(value = "绩效评级")
    private String achievementsRating;


}
