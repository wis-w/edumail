package com.edu.gulimall.product.controller;

import com.edu.common.utils.PageUtils;
import com.edu.common.utils.R;
import com.edu.gulimall.product.entity.BrandEntity;
import com.edu.gulimall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;



/**
 * 品牌
 *
 * @author wis
 * @email wyg@qq.com
 * @date 2020-08-26 21:45:34brand
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
   // @RequiresPermissions("product:brand:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    // @RequiresPermissions("product:brand:info")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:brand:update")
    public R update(@Valid @RequestBody BrandEntity brand){
        // 此处不再使用BindingResult result 而使用统一的异常处理机制  com.edu.gulimall.product.exeception.GulimallExeceptionControllerAdvice
        //public R update(@Valid @RequestBody BrandEntity brand, BindingResult result){
//        if(result.hasErrors()){
//            Map<String,String> map = new HashMap<>();
//            // 获取的校验结果
//            result.getFieldErrors().forEach((item) -> {
//                // FileError 获取到的校验错误
//                String message = item.getDefaultMessage();
//                // 获取错误的属性名
//                String field = item.getField();
//                map.put(field, message);
//            });
//            return R.error(400, "提交的信息不合法").put("data", map);
//        }

		brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("product:brand:save")
    public R save(@Valid @RequestBody BrandEntity brand){
//        if(result.hasErrors()){
//            Map<String,String> map = new HashMap<>();
//            // 获取的校验结果
//            result.getFieldErrors().forEach((item) -> {
//                // FileError 获取到的校验错误
//                String message = item.getDefaultMessage();
//                // 获取错误的属性名
//                String field = item.getField();
//                map.put(field, message);
//            });
//            return R.error(400, "提交的信息不合法").put("data", map);
//        }
        brandService.save(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("product:brand:delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
