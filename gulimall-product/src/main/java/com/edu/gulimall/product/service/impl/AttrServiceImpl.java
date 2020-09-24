package com.edu.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.common.constant.ProductConstant;
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
import com.edu.gulimall.product.vo.AttrGroupRelationVo;
import com.edu.gulimall.product.vo.AttrRespVo;
import com.edu.gulimall.product.vo.AttrVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
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
        if (attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
        }

    }

    /**
     * 规格参数详细查询
     * @param params
     * @param catelogId
     * @param attrType
     * @return
     */
    @Override
    public PageUtils queryBaseAttr(Map<String, Object> params, Long catelogId, String attrType) {
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>().
                eq("attr_type", "base".equalsIgnoreCase(attrType) ? ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()
                        : ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());

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
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);
            // 创建页面显示的Vo对象 因为AttrVo缺少所属分类，所属分组属性
            if ("base".equalsIgnoreCase(attrType)) {

                AttrAttrgroupRelationEntity attrAttrgroupRelationEntity =
                        attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().
                                eq("attr_id", attrEntity.getAttrId()));
                if (attrAttrgroupRelationEntity != null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                    log.info("属性分组信息：{}", attrGroupEntity.toString());
                }
            }
            // 查询分组信息名称
            CategoryEntity entity = categoryDao.selectById(attrEntity.getCatelogId());
            if (entity != null) {
                attrRespVo.setCatelogName(entity.getName());
                log.info("三级分类信息：{}", entity.toString());
            }
            return attrRespVo;
        }).collect(Collectors.toList());
        log.info("商品属性查询：{}", attrRespVoList.toString());
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
        AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao.
                selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));

        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            // 设置分组信息
            if (relationEntity != null) {
                attrRespVo.setAttrGroupId(relationEntity.getAttrGroupId());// 分组ID
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                if (attrGroupEntity != null) {
                    attrRespVo.setAttrName(attrGroupEntity.getAttrGroupName());// 获取分组名
                }

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
    @Transactional// 事务开启
    @Override
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);

        this.updateById(attrEntity);// 基本信息修改

        if (attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
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

    /**
     * 根据分组ID查找关联的额所有基本属性
     * @param attrGroupId
     * @return
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrGroupId) {

        // 查询所有中间表的attr_id的所有值
        QueryWrapper<AttrAttrgroupRelationEntity> entityQueryWrapper = new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrGroupId);
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntities = attrAttrgroupRelationDao.selectList(entityQueryWrapper);

        // 方案不太好，可以默默忽略
//        List<AttrEntity> collect = attrAttrgroupRelationEntities.stream().map((entity) -> {
//            // 查询改组下的所有西信息
//            AttrEntity attrEntity = this.baseMapper.selectById(entity.getAttrId());
//            return attrEntity;
//        }).collect(Collectors.toList());

        List<Long> collect = attrAttrgroupRelationEntities.stream().map(attr -> attr.getAttrId()).collect(Collectors.toList());

        log.info("删除的数组信息:{}",collect.toString());

        if (collect == null || collect.size() == 0) {
            return null;
        }
        List<AttrEntity>list = (List<AttrEntity>) this.listByIds(collect);
        return list;
    }

    /**
     * 删除属性分组的关联关系
     * @param vos
     */
    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
//        QueryWrapper<AttrAttrgroupRelationEntity> queryWrapper = new QueryWrapper<>();
//        for (AttrGroupRelationVo vo : vos) {
//            queryWrapper.eq("attr_id", vo.getAttrId()).eq("attr_group_id", vo.getGroupId());
//        }
//        this.remove(wapper)
        // 将也买你数据收集成为一个集合
        List<AttrAttrgroupRelationEntity> collect = Arrays.asList(vos).stream().map((item) -> {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, attrAttrgroupRelationEntity);
            return attrAttrgroupRelationEntity;
        }).collect(Collectors.toList());
        log.info("删除的数组信息2:{}",collect.toString());

        attrAttrgroupRelationDao.deleteBatchRelation(collect);
    }

    /**
     * 获取可进行关联，但未关联的属性列表
     * @param attrgroupId
     * @param params
     * @return
     */
    @Override
    public PageUtils getNoRelation(Long attrgroupId, Map<String, Object> params) {
        //1、当前分组只能关联自己所属的分类里面的所有属性
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();
        //2、当前分组只能关联别的分组没有引用的属性
        //2.1)、当前分类下的其他分组
        List<AttrGroupEntity> group = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        List<Long> collect = group.stream().map(item -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());

        //2.2)、这些分组关联的属性
        List<AttrAttrgroupRelationEntity> groupId = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", collect));
        List<Long> attrIds = groupId.stream().map(item -> {
            return item.getAttrId();
        }).collect(Collectors.toList());

        //2.3)、从当前分类的所有属性中移除这些属性；
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId).eq("attr_type",ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if(attrIds!=null && attrIds.size()>0){
            wrapper.notIn("attr_id", attrIds);
        }
        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            wrapper.and((w)->{
                w.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);

        PageUtils pageUtils = new PageUtils(page);

        return pageUtils;
    }

}