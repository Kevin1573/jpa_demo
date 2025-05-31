package com.xboot.jpa.demo.common.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 注释
 *
 * @author xboot
 **/
@Configuration
public class SaSecurityConfig implements WebMvcConfigurer {
    protected static final String[] WHITE_LIST_PATTERN = new String[]{
            "/**/login",
            "/**/logout",
            "/**/error",
            "/**/favicon.ico",
            "/**/static/**",
            "/**/druid/**",
            "/**/swagger-ui.html",
            "/**/webjars/**",
            "/**/v2/**",
            "/**/swagger-resources/**",
            "/favicon.ico"
    };

    /**
     * https://sa-token.cc/doc.html#/use/route-check?id=_1%e3%80%81%e6%b3%a8%e5%86%8c-sa-token-%e8%b7%af%e7%94%b1%e6%8b%a6%e6%88%aa%e5%99%a8
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/login",
                        "/health",
                        "/*.ico",
                        "/job/*",
                        "/recover/*",
                        "/random/*",
                        "/retry/*");
    }

    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // 是否允许请求带有验证信息
        config.setAllowCredentials(true);

        // 允许访问的客户端域名
        // (springboot2.4以上的加入这一段可解决 allowedOrigins cannot contain the special value "*"问题)
        List<String> allowedOriginPatterns = new ArrayList<>();
        allowedOriginPatterns.add("*");
        config.setAllowedOriginPatterns(allowedOriginPatterns);

        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 对接口配置跨域设置
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
