package com.edu.gulimall.product.controller;

import com.edu.common.utils.PageUtils;
import com.edu.common.utils.R;
import com.edu.gulimall.product.entity.AttrEntity;
import com.edu.gulimall.product.entity.AttrGroupEntity;
import com.edu.gulimall.product.service.AttrGroupService;
import com.edu.gulimall.product.service.AttrService;
import com.edu.gulimall.product.service.CategoryService;
import com.edu.gulimall.product.vo.AttrGroupRelationVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 属性分组
 *
 * @author wis
 * @email wyg@qq.com
 * @date 2020-08-26 21:45:34
 */
@Slf4j
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    CategoryService categoryService;

    @Resource
    AttrService attrService;

    /**
     * 关联分组，查询未在该分类的可分类属性
     *
     * @return
     */
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R attrNotRelation(@PathVariable("attrgroupId") Long attrgroupId,@RequestParam Map<String, Object> params) {
        log.info("关联分组，查询未在该分类的可分类属性");
        PageUtils pageUtils = attrService.getNoRelation(attrgroupId, params);
        return R.ok().put("page", pageUtils);
    }

    /**
     * 删除属性分组信息
     * @param vos
     * @return
     */
    @PostMapping("/attr/relation/delete")
    public R relationDelete(@RequestBody AttrGroupRelationVo[] vos) {
        log.info("删除的属性数组：{}", vos.toString());
        attrService.deleteRelation(vos);
        return R.ok();
    }

    /**
     * 属性分组，关联关系
     * @return
     */
    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId){
        log.info("查询的属性ID：{}", attrgroupId);
        List<AttrEntity> list = attrService.getRelationAttr(attrgroupId);
        return R.ok().put("data", list);
    }

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
   // @RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params,@PathVariable("catelogId") Long catelogId){
//        PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params,catelogId);
        log.info("获取的page信息:{}", new Object[]{page});
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    // @RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

        Long catelogId = attrGroup.getCatelogId();
        Long[] path = categoryService.findCateLogPath(catelogId);

        attrGroup.setCatelogPath(path);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
   // @RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
