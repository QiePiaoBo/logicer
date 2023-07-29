package com.dylan.licence;


import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bootstrap.BootstrapConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author Dylan
 * @Description 用户及权限管理
 */
@SpringBootApplication(scanBasePackages = {"com.dylan.licence", "com.dylan.framework"})
@EnableRedisHttpSession
@EnableDiscoveryClient
public class LicenceApplication {

    private static final MyLogger logger = MyLoggerFactory.getLogger(LicenceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LicenceApplication.class, args);
        logger.info("LicenceApplication started.");
    }
}
