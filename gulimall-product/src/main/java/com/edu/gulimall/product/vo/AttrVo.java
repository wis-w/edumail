package com.edu.gulimall.product.vo;

import lombok.Data;

/**
 * 商品属性Vo
 *
 * @author wis
 * @email wyg@qq.com
 * @date 2020-08-26 20:43:05
 */
@Data
public class AttrVo{

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

	private Long attrGroupId;

}
