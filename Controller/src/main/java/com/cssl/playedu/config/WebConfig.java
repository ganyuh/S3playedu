package com.cssl.playedu.config;

import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import java.nio.charset.StandardCharsets;

/**
 * @Author : Tang
 * @CreateDate 2023/8/29 11:15
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    //     拦截器跨域配置
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        System.out.println("WebConfig.addCorsMappings() Running");
//        // 跨域路径
//        CorsRegistration cors = registry.addMapping("/**");
//
//        // 可访问的外部域
//        cors.allowedOrigins("*");
//        // 支持跨域用户凭证
////        cors.allowCredentials(true);
//        cors.allowedOriginPatterns("*");
//        // 设置 header 能携带的信息
//        cors.allowedHeaders("*");
//        // 支持跨域的请求方法
//        cors.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
//        // 设置跨域过期时间，单位为秒
//        cors.maxAge(3600);
//    }
}
