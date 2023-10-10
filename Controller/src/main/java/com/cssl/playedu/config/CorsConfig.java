package com.cssl.playedu.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Author : Tang
 * @CreateDate 2023/8/30 15:51
 */
@Configuration
public class CorsConfig {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOriginPattern("*");
        // 预检请求的有效期，单位为秒。
        corsConfiguration.setMaxAge(3600L);
        // 是否支持安全证书(必需参数)
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> filterRegistrationBean() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0);//提升优先级
        return bean;
    }
}
