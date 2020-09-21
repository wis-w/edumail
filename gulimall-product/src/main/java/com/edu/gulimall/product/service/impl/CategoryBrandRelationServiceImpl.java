package com.edu.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.common.utils.PageUtils;
import com.edu.common.utils.Query;
import com.edu.gulimall.product.dao.BrandDao;
import com.edu.gulimall.product.dao.CategoryBrandRelationDao;
import com.edu.gulimall.product.dao.CategoryDao;
import com.edu.gulimall.product.entity.BrandEntity;
import com.edu.gulimall.product.entity.CategoryBrandRelationEntity;
import com.edu.gulimall.product.entity.CategoryEntity;
import com.edu.gulimall.product.service.CategoryBrandRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Resource
    BrandDao brandDao;

    @Resource
    CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetils(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();
        // 查询品牌详细名称
        BrandEntity brandEntitie = brandDao.selectById(brandId);

        // 查询三级分类信息
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);

        categoryBrandRelation.setBrandName(brandEntitie.getName());
        categoryBrandRelation.setCatelogName(categoryEntity.getName());

        this.save(categoryBrandRelation);
    }

    /**
     * 更新关联表的品牌信息
     * @param brandId
     * @param name
     */
    @Override
    public void updateBrand(Long brandId, String name) {
        // 创建中间表的实体类
        CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
        categoryBrandRelationEntity.setBrandId(brandId);
        categoryBrandRelationEntity.setBrandName(name);

        log.info("关联表更新brand_id:{}", brandId);
        this.update(categoryBrandRelationEntity,
                new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
    }

    /**
     * 级联更新中间表数据
     * @param catId
     * @param name
     */
    public void updateCategory1(Long catId, String name) {
        CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
        categoryBrandRelationEntity.setCatelogId(catId);
        categoryBrandRelationEntity.setCatelogName(name);
        log.info("关联表更新catelog_id:{}", catId);
        this.update(categoryBrandRelationEntity, new UpdateWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));
    }

    /**
     * 级联更新中间表数据 实际效果如updateCategory1（） 仅做不同的实现
     * @param catId
     * @param name
     */
    @Override
    public void updateCategory(Long catId, String name) {
        log.info("关联表更新catId:{},name:{}", catId, name);
        this.baseMapper.updateCategory(catId, name);
    }

}