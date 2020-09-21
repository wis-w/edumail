package com.edu.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.common.utils.PageUtils;
import com.edu.common.utils.Query;
import com.edu.gulimall.product.dao.BrandDao;
import com.edu.gulimall.product.entity.BrandEntity;
import com.edu.gulimall.product.service.BrandService;
import com.edu.gulimall.product.service.CategoryBrandRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Resource
    CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 品牌管理查询功能
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        // 1、获取到key
        String key = (String) params.get("key");

        QueryWrapper<BrandEntity> wrapper = new QueryWrapper<>();
        // 2、模糊匹配的设置
        if(!StringUtils.isEmpty(key)){
            wrapper.eq("brand_id", key).or().like("name", key);
        }

        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    /**
     * 细节更新操作
     * @param brand
     */
    @Transactional// 事务控制
    @Override
    public void updateByIdDetail(BrandEntity brand) {
        // 考虑到所有冗余字段的数据一致性
        this.updateById(brand);
        if(!StringUtils.isEmpty(brand.getName())){
            // 更新关联表的冗余字段
            log.info("进行关联表数据修改");
            categoryBrandRelationService.updateBrand(brand.getBrandId(), brand.getName());

            //TODO 更新其他关联信息

        }
    }

}