package com.edu.gulimall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 配置中心设置
 * 1、 <dependency>
 *             <groupId>com.alibaba.cloud</groupId>
 *             <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
 *    </dependency>
 *  2、创建bootstrap.properties配置文件
 *          spring.application.name=gulimall-coupon
 *          spring.cloud.nacos.config.server-addr=localhost:8848
 *  3、给配置中心默认添加配置集，默认规则：应用名.properties
 *  4、给 应用名.properties 增加响应的配置
 *  5、动态的获取配置
 *      @RefreshScope// 动态刷新配置文件
 *      @Value("${配置属性})
 *      当配置中心的配置修改时，程序会自动刷新该配置
 *      加载顺序：外部配置>内部配置，properties>yaml,bootstrap>application
 */
@SpringBootApplication
@EnableDiscoveryClient// 开启服务注册功能，可以将该服务注册到nacos中
public class CouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponApplication.class, args);
    }

}
