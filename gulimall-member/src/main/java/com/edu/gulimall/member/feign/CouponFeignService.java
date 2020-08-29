package com.edu.gulimall.member.feign;

import com.edu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 声明式远程调用接口
 */
@FeignClient("gulimall-coupin")// 表示这是一个远程调用接口,需要调用gulimall-coupin服务
public interface CouponFeignService {
    @RequestMapping("/coupon/coupon/member/list")
    public R memberCoupons();
}
