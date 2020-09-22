package com.edu.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.common.utils.PageUtils;
import com.edu.common.utils.Query;
import com.edu.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.edu.gulimall.product.dao.AttrDao;
import com.edu.gulimall.product.dao.AttrGroupDao;
import com.edu.gulimall.product.dao.CategoryDao;
import com.edu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.edu.gulimall.product.entity.AttrEntity;
import com.edu.gulimall.product.entity.AttrGroupEntity;
import com.edu.gulimall.product.entity.CategoryEntity;
import com.edu.gulimall.product.service.AttrService;
import com.edu.gulimall.product.service.CategoryService;
import com.edu.gulimall.product.vo.AttrRespVo;
import com.edu.gulimall.product.vo.AttrVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    /**
     * 属性&属性分组关联
     */
    @Resource
    AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    /**
     * 属性分组
     */
    @Resource
    AttrGroupDao attrGroupDao;

    /**
     * 三级分类
     */
    @Resource
    CategoryDao categoryDao;

    /**
     * 商品三级分类
     */
    @Resource
    CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 规格参数级联保存
     * @param attr
     */
    @Override
    @Transactional// 事务开启
    public void saveAttr(AttrVo attr) {
        log.info("规格参数实体:{}", attr.toString());
        // 基本信息保存
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.save(attrEntity);// 保存成功后返回attrEntry的attrId属性
        // 保存关联关系
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
        attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
        attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);

    }

    /**
     * 规格参数详细查询
     * @param params
     * @param catelogId
     * @return
     */
    @Override
    public PageUtils queryBaseAttr(Map<String, Object> params, Long catelogId) {
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
        if (catelogId != 0) {
            wrapper.eq("show_desc", catelogId);// 如果id存在，则进行精准查询
        }
        String key = (String) params.get("key");
        // 封装查询条件，进行模糊匹配查询
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((wrapperQuery)->{
                wrapperQuery.eq("attr_id", key).or().like("attr_name", key);
            });
        }
        // 分页查询出主表的数据信息
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper
        );

        PageUtils pageUtils = new PageUtils(page);
        // 获取所有商品属性表信息
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> attrRespVoList = records.stream().map((attrEntity) -> {
            // 创建页面显示的Vo对象 因为AttrVo缺少所属分类，所属分组属性
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
            if (attrAttrgroupRelationEntity != null) {
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
                attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                log.info("属性分组信息：{}", attrGroupEntity.toString());
            }
            // 查询分组信息名称
            CategoryEntity entity = categoryDao.selectById(attrEntity.getCatelogId());
            if (entity != null) {
                attrRespVo.setCatelogName(entity.getName());
                log.info("三级分类信息：{}", entity.toString());
            }
            return attrRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(attrRespVoList);

        return pageUtils;
    }

    /**
     * 查询修改所需的所有信息
     * @param attrId
     * @return
     */
    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrRespVo attrRespVo = new AttrRespVo();
        AttrEntity attrEntity = this.getById(attrId);// 查询出属性表的所有信息
        BeanUtils.copyProperties(attrEntity, attrRespVo);// 复制到页面的返回Vo对象中
        AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));

        // 设置分组信息
        if (relationEntity != null) {
            attrRespVo.setAttrGroupId(relationEntity.getAttrGroupId());// 分组ID
            AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
            if (attrGroupEntity != null) {
                attrRespVo.setAttrName(attrGroupEntity.getAttrGroupName());// 获取分组名
            }

        }

        // 设置分类信息
        Long attrId1 = attrEntity.getCatelogId();

        Long[] cateLogPath = categoryService.findCateLogPath(attrId1);
        attrRespVo.setCatelogPath(cateLogPath);// 分组路径
        CategoryEntity entity = categoryDao.selectById(attrId1);
        if (entity != null) {
            attrRespVo.setCatelogName(entity.getName());// 分类名称
        }

        return attrRespVo;
    }

    /**
     * 修改商品信息相关属性
     * @param attr
     */
    @Transactional
    @Override
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);

        this.updateById(attrEntity);// 基本信息修改

        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        BeanUtils.copyProperties(attr, attrAttrgroupRelationEntity);
        // 查询表中是否含有该数据，如果有则修改，否则新增一条记录
        Integer count = attrAttrgroupRelationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));

        if (count > 0) {
            // 修改分组关联系信息
            attrAttrgroupRelationDao.update(attrAttrgroupRelationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
        } else {
            // 新增关联关系
            attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
        }
    }

}