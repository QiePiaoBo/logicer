package com.dylan.framework.config;

import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * @Classname GlobalCorsConfig
 * @Description GlobalCorsConfig
 * @Date 8/22/2023 6:32 PM
 */
@Configuration
public class GlobalConfig {

    private static final MyLogger logger = MyLoggerFactory.getLogger(GlobalConfig.class);
    /**
     * 跨域设置
     * @return
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            //重写父类提供的跨域请求处理的接口
            public void addCorsMappings(CorsRegistry registry) {
                //添加映射路径
                registry.addMapping("/**")
                        //放行哪些原始域(请求方式)
                        .allowedMethods("GET","POST", "PUT", "DELETE","OPTIONS")
                        //是否发送Cookie信息
                        .allowCredentials(true)
                        //放行哪些原始域
                        .allowedOriginPatterns("*://localhost:*", "*://logicer.top:*")
                        //放行哪些原始域(头部信息)
                        .allowedHeaders("*");
                        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
                        //.exposedHeaders("Header1", "Header2");
                        //.allowedOrigins("*")
            }
        };
    }

    /**
     * 重置返回的cookie中的一些默认属性值
     * @return
     */
    @Bean
    public DefaultCookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setSameSite("None");
        serializer.setUseSecureCookie(true);
        return serializer;
    }


    /**
     * 应用程序不会在启动时执行任何自定义操作来配置Redis连接。这对于大多数应用程序来说都是足够的，因为Spring Session会自动配置Redis连接
     * @return
     */
    @Bean
    public static ConfigureRedisAction configureRedisAction(){
        return ConfigureRedisAction.NO_OP;
    }
}
