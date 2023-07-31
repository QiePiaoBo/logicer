package com.dylan.licence;


import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Dylan
 * @Description 用户及权限管理
 */
@SpringBootApplication(scanBasePackages = {"com.dylan.licence", "com.dylan.framework"})
@EnableDiscoveryClient
@EnableDubbo
public class LicenceApplication {

    private static final MyLogger logger = MyLoggerFactory.getLogger(LicenceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LicenceApplication.class, args);
        logger.info("LicenceApplication started.");
    }
}
