package com.edu.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品属性
 * 
 * @author wis
 * @email wyg@qq.com
 * @date 2020-08-26 20:43:05
 */
@Data
@TableName("pms_attr")
public class AttrEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId
	private Long attrId;
	/**
	 * $column.comments
	 */
	private String attrName;
	/**
	 * $column.comments
	 */
	private Integer searchType;
	/**
	 * $column.comments
	 */
	private String icon;
	/**
	 * $column.comments
	 */
	private String valueSelect;
	/**
	 * $column.comments
	 */
	private Integer attrType;
	/**
	 * $column.comments
	 */
	private Long enable;
	/**
	 * $column.comments
	 */
	private Long catelogId;
	/**
	 * $column.comments
	 */
	private Integer showDesc;

//	@TableField(exist = false)
//	private Long attrGroupId;

}
