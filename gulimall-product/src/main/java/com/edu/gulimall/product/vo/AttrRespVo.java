package com.edu.gulimall.product.vo;

import lombok.Data;

/**
 * 继承attrVo的所有属性值
 */
@Data
public class AttrRespVo extends AttrVo{

    /**
     * 所属分类
     */
    private String catelogName;

    /**
     * 所属分组
     */
    private String groupName;

    /**
     * 分类路径数组
     */
    private Long[] catelogPath;


}


