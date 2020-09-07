package com.edu.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.common.utils.PageUtils;
import com.edu.common.utils.Query;
import com.edu.gulimall.product.dao.CategoryDao;
import com.edu.gulimall.product.entity.CategoryEntity;
import com.edu.gulimall.product.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
//    继承的ServiceImpl<CategoryDao, CategoryEntity>中已经存在了baseMapper,相当于已经进行了注入，不可以反复注入
//    @Autowired
//    CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        // 查出所有的分类 不传入条件相当于全表查询
        List<CategoryEntity> entities = baseMapper.selectList(null);

        // 组装成父子的树形结构
        // 找到所有的1级分类 父亲id为0
        List<CategoryEntity> level1Menus = entities.stream().filter((categoryEntity) -> {
            return categoryEntity.getParentCid() == 0;
        }).map((menu) -> {
            menu.setChildren(getChildrenList(menu, entities));// 采用递归的方式收集子菜单
            return menu;
        }).sorted((m1, m2) -> {
            return (m1.getSort() == null ? 0 : m1.getSort()) - (m2.getSort() == null ? 0 : m2.getSort());// 进行排序
        }).collect(Collectors.toList());// 使用streamApi，首先过滤出父节点id==0 ， 然后收集成list

        return level1Menus;
    }

    /**
     * 菜单的批量删除操作
     * @param asList
     */
    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO 检查是否已经被引用

        // 逻辑删除
        baseMapper.deleteBatchIds(asList);

    }

    /**
     * 采用递归的方式进行菜单收集，所有菜单的子菜单
     *
     * @param root 当前菜单
     * @param all  全部菜单
     * @return
     */
    private List<CategoryEntity> getChildrenList(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> clildren = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == root.getCatId();// 找到父级菜单
        }).map(categoryEntity -> {
            categoryEntity.setChildren(getChildrenList(categoryEntity, all));// 递归查询该菜单下的子菜单
            return categoryEntity;
        }).sorted((m1, m2) -> {
            return (m1.getSort() == null ? 0 : m1.getSort()) - (m2.getSort() == null ? 0 : m2.getSort());// 进行菜单排序
        }).collect(Collectors.toList());// 收集返回菜单

        return clildren;
    }

}