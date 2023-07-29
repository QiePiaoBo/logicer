package com.dylan.blog.config;


import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname RedissonConfig
 * @Description RedissonConfig
 * @Date 9/7/2022 3:17 PM
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.redis", name = "host")
public class RedissonConfig {

    private static final MyLogger log = MyLoggerFactory.getLogger(RedissonConfig.class);

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password: }")
    private String password;

    @Bean("redissonClient")
    public RedissonClient getRedisson(){
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig
                .setAddress("redis://" + host + ":" + port);
        if (StringUtils.isNotBlank(password)){
            singleServerConfig.setPassword(password);
        }
        return Redisson.create(config);
    }

}
