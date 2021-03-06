package com.edu.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.common.utils.PageUtils;
import com.edu.gulimall.product.entity.SpuInfoEntity;

import java.util.Map;

/**
 * spu信息
 *
 * @author wis
 * @email wyg@qq.com
 * @date 2020-08-26 20:43:05
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

