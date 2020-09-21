package com.edu.gulimall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.gulimall.product.entity.CategoryBrandRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌分类关联
 * 
 * @author wis
 * @email wyg@qq.com
 * @date 2020-08-26 20:43:05
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {

    void updateCategory(@Param("catelogId") Long catId, @Param("name") String name);
}
