package com.dylan.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @Classname FilesApplication
 * @Description FilesApplication
 * @Date 9/14/2022 6:52 PM
 */
@EnableRedisHttpSession
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.dylan.file", "com.dylan.framework"})
public class FilesApplication {

    public static void main(String[] args) {

        SpringApplication.run(FilesApplication.class, args);

    }
}
