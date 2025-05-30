package com.xboot.jpa.demo.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token 权限认证配置类
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    /**
     * 注册Sa-Token的拦截器，配置认证规则
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册登录认证拦截器
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns(
                        // Swagger相关路径
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/favicon.ico",
                        // 登录接口
                        "/auth/login",
                        // 公开接口
                        "/public/**"
                );
    }

    /**
     * Sa-Token基础配置
     */
//    @Bean
//    public SaTokenConfig getSaTokenConfig() {
//        SaTokenConfig config = new SaTokenConfig();
//        config.setTokenName("satoken");             // token名称
//        config.setTimeout(30 * 24 * 60 * 60);       // token有效期30天
//        config.setActivityTimeout(-1);              // 无操作永不过期
//        config.setIsConcurrent(true);               // 允许并发登录
//        config.setIsShare(true);                    // 共享token
//        config.setTokenStyle("uuid");               // token风格
//        config.setIsReadBody(true);                 // 从请求体读取token
//        config.setIsReadHeader(true);               // 从请求头读取token
//        config.setIsReadCookie(true);               // 从Cookie读取token
//        return config;
//    }

    /**
     * 注册Sa-Token全局过滤器
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                .addInclude("/**")
                .addExclude("/favicon.ico")
                .setAuth(obj -> {
                    // 登录认证校验
                    if (!SaHolder.getRequest().getRequestPath().startsWith("/auth/")) {
                        StpUtil.checkLogin();
                    }
                })
                .setError(e -> {
                    // 错误处理
                    return SaResult.error(e.getMessage());
                });
    }
}
