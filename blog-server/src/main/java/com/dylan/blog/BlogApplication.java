package com.dylan.blog;

import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author Dylan
 */
@SpringBootApplication(scanBasePackages = {"com.dylan.blog", "com.dylan.framework"})
@EnableRedisHttpSession
@EnableDiscoveryClient
@EnableCaching
@EnableDubbo
public class BlogApplication {

    private static final MyLogger logger = MyLoggerFactory.getLogger(BlogApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
        logger.info("BlogApplication started.");
    }
}
