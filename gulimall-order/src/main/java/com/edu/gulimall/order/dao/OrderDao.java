package com.edu.gulimall.order.dao;

import com.edu.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author wis
 * @email wyg@qq.com
 * @date 2020-08-27 21:35:57
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
