package com.edu.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 品牌
 * 
 * @author wis
 * @email wyg@qq.com
 * @date 2020-08-26 20:43:05
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotEmpty
	@NotBlank(message = "品牌名必须提交")
	private String name;
	/**
	 * LOGO名称
	 */
	@NotEmpty
	@URL(message = "logo必须是个合法的地址")
	private String logo;
	/**
	 * 介绍
	 */
	@NotEmpty
	@NotBlank(message = "介绍必须提交")
	private String descript;
	/**
	 * 显示状态 1：显示 0：不显示
	 */
	@Min(value = 0, message = "显示状态 1：显示 0：不显示")
	@Max(value = 1, message = "显示状态 1：显示 0：不显示")
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@NotEmpty
	@Pattern(regexp="/^[a-zA-Z]$/",message = "检索首字母必须时一个字母")
	private String firstLetter;
	/**
	 * 排序
	 */
	@NotNull
	@Min(value = 0, message = "排序必须大于等于0")
	private Integer sort;

}
