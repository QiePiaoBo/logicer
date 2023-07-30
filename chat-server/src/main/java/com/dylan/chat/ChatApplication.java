package com.dylan.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Classname ChatApplication
 * @Description ChatApplication
 * @Date 6/16/2022 5:56 PM
 */

@SpringBootApplication(scanBasePackages = {"com.dylan.chat","com.dylan.framework", "com.dylan.licence"})
@EnableDiscoveryClient
@EnableFeignClients
public class ChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

}
