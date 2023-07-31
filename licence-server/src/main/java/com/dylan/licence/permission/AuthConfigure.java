package com.dylan.licence.permission;

import com.dylan.framework.interceptor.AuthInterceptor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.annotation.Resource;

/**
 * 拦截器注册中心
 * @author Dylan
 */
@RefreshScope
@Configuration
public class AuthConfigure implements WebMvcConfigurer {

    @Resource
    private AuthInterceptor interceptor;

    final String urls = "/**";
    /**
     * 注册权限校验拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns(urls);
    }
}
