package com.edu.gulimall.coupon.controller;

import com.edu.common.utils.PageUtils;
import com.edu.common.utils.R;
import com.edu.gulimall.coupon.entity.CouponEntity;
import com.edu.gulimall.coupon.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;



/**
 * 优惠券信息
 *
 * @author wis
 * @email wyg@qq.com
 * @date 2020-08-27 21:07:38
 */
@Slf4j
@RefreshScope// 动态刷新配置文件
@RestController
@RequestMapping("coupon/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @Value("${coupon.user.name}")
    private String name;

    @Value("${coupon.user.age}")
    private Integer age;

    /**
     * 配置管理测试
     * @return
     */
    @RequestMapping("/member/config/test")
    public R test(){
        return R.ok().put("age", age).put("name", name);
    }

    /**
     * 远程调用测试
     * @return
     */
    @RequestMapping("/member/list")
    public R memberCoupons(){
        log.info("{}简单的远程调用测试，没有业务意义",new Object[]{"优惠券服务："});
        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setAmount(new BigDecimal("1.0"));
        couponEntity.setCouponName("会员优惠");
        return R.ok().put("coupons", Arrays.asList(couponEntity));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
   // @RequiresPermissions("coupon:coupon:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = couponService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    // @RequiresPermissions("coupon:coupon:info")
    public R info(@PathVariable("id") Long id){
		CouponEntity coupon = couponService.getById(id);

        return R.ok().put("coupon", coupon);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
   // @RequiresPermissions("coupon:coupon:save")
    public R save(@RequestBody CouponEntity coupon){
		couponService.save(coupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("coupon:coupon:update")
    public R update(@RequestBody CouponEntity coupon){
		couponService.updateById(coupon);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("coupon:coupon:delete")
    public R delete(@RequestBody Long[] ids){
		couponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
