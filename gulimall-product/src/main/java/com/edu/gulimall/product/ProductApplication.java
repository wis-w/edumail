package com.edu.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 整合mybatis Plus
 * 1、导入依赖
 * 2、配置
 *  1）配置数据源：导入驱动->配置数据源
 *  2）配置mybatis Plus相关信息:使用@MapperScan扫描->配置映射文件位置
 */
@MapperScan("com.edu.gulimall.product.dao")
@SpringBootApplication
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

}
