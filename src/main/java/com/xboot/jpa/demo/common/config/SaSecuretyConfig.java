package com.xboot.jpa.demo.common.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注释
 *
 * @author xboot
 **/
@Configuration
public class SaSecuretyConfig implements WebMvcConfigurer {
    public static final String[] WHITE_LIST_PATTERN = new String[]{
            "/**/login",
            "/**/logout",
            "/**/error",
            "/**/favicon.ico",
            "/**/static/**",
            "/**/druid/**",
            "/**/swagger-ui.html",
            "/**/webjars/**",
            "/**/v2/**",
            "/**/swagger-resources/**"
    };

    /**
     * https://sa-token.cc/doc.html#/use/route-check?id=_1%e3%80%81%e6%b3%a8%e5%86%8c-sa-token-%e8%b7%af%e7%94%b1%e6%8b%a6%e6%88%aa%e5%99%a8
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/login");
    }


}
