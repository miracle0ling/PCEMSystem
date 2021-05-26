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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author Blade
 * @since 2021-01-08
 */
@Data
@TableName("blade_vocation")
@ApiModel(value = "Vocation对象", description = "Vocation对象")
public class Vocation implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 员工姓名
	 */
	@ApiModelProperty(value = "员工姓名")
	private String staffName;
	/**
	 * 员工id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "员工id")
	private Long staffId;
	/**
	 * 原因
	 */
	@ApiModelProperty(value = "原因")
	private String reason;
	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "开始时间")
	private LocalDateTime beginTime;
	/**
	 * 结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "结束时间")
	private LocalDateTime endTime;
	/**
	 * 状态1批准2未批准
	 */
	@ApiModelProperty(value = "状态1批准2未批准")
	private Integer status;
	/**
	 * 类型1请假2加班
	 */
	@ApiModelProperty(value = "类型1请假2加班")
	private Integer type;
	/**
	 * 附加说明（可以是图片）
	 */
	@ApiModelProperty(value = "附加说明（可以是图片）")
	private String remark;
	/**
	 * 是否已删除
	 */
	@ApiModelProperty(value = "是否已删除")
	private Integer isDeleted;

	@ApiModelProperty(value = "部门id")
	private String deptId;

	@ApiModelProperty(value = "工号")
	private String staffNumber;

}
