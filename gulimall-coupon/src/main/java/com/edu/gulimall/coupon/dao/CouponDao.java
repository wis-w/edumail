package com.edu.gulimall.coupon.dao;

import com.edu.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author wis
 * @email wyg@qq.com
 * @date 2020-08-27 21:07:38
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
