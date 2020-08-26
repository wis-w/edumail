package com.edu.gulimall.product.dao;

import com.edu.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author wis
 * @email wyg@qq.com
 * @date 2020-08-26 20:43:05
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
