package com.edu.gulimall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class GulimallCorsConfiguartion {

    /**
     * 跨域问题拦截
     * @return
     */
    @Bean
    public CorsWebFilter crosWebFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 1、配置跨域
        corsConfiguration.addAllowedHeader("*");// 所有请求头允许
        corsConfiguration.addAllowedMethod("*");// 所有类型请求允许
        corsConfiguration.addAllowedOrigin("*");// 所有的请求方式允许
        corsConfiguration.setAllowCredentials(true);// 可以携带cookies

        source.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsWebFilter(source);
    }

}
