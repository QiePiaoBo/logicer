package com.dylan.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Classname ChatApplication
 * @Description ChatApplication
 * @Date 6/16/2022 5:56 PM
 */
@SpringBootApplication(scanBasePackages = {"com.dylan.chat","com.dylan.framework", "com.dylan.licence"})
@EnableDiscoveryClient
public class ChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

}
