package com.edu.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.common.vaild.AddGroup;
import com.edu.common.vaild.ListValue;
import com.edu.common.vaild.UpdateGroup;
import com.edu.common.vaild.UpdateStatusGroup;
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
	 * 品牌ID
	 */
	@TableId
	@NotNull(message = "修改必须指定ID",groups = {UpdateGroup.class, UpdateStatusGroup.class})
	@Null(message = "新增不可指定ID",groups = {AddGroup.class})
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotBlank(message = "品牌名必须提交",groups = {UpdateGroup.class,AddGroup.class})
	private String name;
	/**
	 * LOGO名称
	 */
	@NotEmpty(message = "logo不为空",groups = {UpdateGroup.class,AddGroup.class})
	@URL(message = "logo必须是个合法的地址",groups = {UpdateGroup.class,AddGroup.class})
	private String logo;
	/**
	 * 介绍
	 */
	@NotBlank(message = "介绍必须提交",groups = {UpdateGroup.class,AddGroup.class})
	private String descript;
	/**
	 * 显示状态 1：显示 0：不显示
	 */
	//@Min(value = 0, message = "显示状态 1：显示 0：不显示",groups = {UpdateGroup.class,AddGroup.class})
	//@Max(value = 1, message = "显示状态 1：显示 0：不显示",groups = {UpdateGroup.class,AddGroup.class}
	@NotNull(groups = {UpdateGroup.class,AddGroup.class, UpdateStatusGroup.class})
	@ListValue(vals={0,1},groups = {UpdateGroup.class,AddGroup.class, UpdateStatusGroup.class})
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@Pattern(regexp="^[a-zA-Z]$",message = "检索首字母必须时一个字母",groups = {UpdateGroup.class,AddGroup.class})
	private String firstLetter;
	/**
	 * 排序
	 */
	@NotNull(groups = {AddGroup.class, UpdateGroup.class})
	@Min(value = 0, message = "排序必须大于等于0", groups = {UpdateGroup.class, AddGroup.class})
	private Integer sort;

}
