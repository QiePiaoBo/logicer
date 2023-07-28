package com.dylan.licence.config;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dylan
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 分页插件设置每一页最大容量
     */
    @Bean
    public PaginationInnerInterceptor getPaginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setMaxLimit(1000L);
        return paginationInnerInterceptor;
    }
}