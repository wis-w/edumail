package com.edu.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 整合mybatis Plus
 * 1、导入依赖
 * 2、配置
 *  1）配置数据源：导入驱动->配置数据源
 *  2）配置mybatis Plus相关信息:使用@MapperScan扫描->配置映射文件位置
 * 3.使用逻辑删除
 *  1）配置全局的逻辑删除规则：可省略
 *      logic-delete-value: 1 #设置逻辑删除
 *      logic-not-delete-value: 0 #设置逻辑删除
 *  2）设置逻辑删除的bean组件 高版本可省略
 *  3）在实体类上加上逻辑删除注解@TableLogic
 * 3。JSR303
 *  给Bean增加校验注解 @Email @NotNUll
 *  Controller增加 @Valid public R save(@Valid @RequestBody BrandEntity brand) 开启校验规则
 *  效果：检验错误会有默认的响应
 *  在校验的bean后面紧跟一个BindingResult就可以得到校验的结果
 *  public R save(@Valid @RequestBody BrandEntity brand, BindingResult result)
 *
 * 4、统一的异常处理 @ControllerAdvice
 *
 */
@EnableDiscoveryClient// 开启服务注册发现功能
@MapperScan("com.edu.gulimall.product.dao")
@SpringBootApplication
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

}
