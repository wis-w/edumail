package com.edu.gulimall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 远程调用的步骤
 * 1、引入OpenFeign
 * 2、编写接口，告诉springCloud这个接口需要进行远程调用
 *     1）声明接口中说明调用的时那个服务的那个接口
 *     2）使用@EnableFeignClients（主启动类），开启远程调用功能
 */
@EnableFeignClients(basePackages = "com.edu.gulimall.member.feign")//开启远程调用功能
@EnableDiscoveryClient// 开启服务注册功能
@SpringBootApplication
public class MemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }

}
