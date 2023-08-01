package com.dylan.comm.config.redis;

import com.dylan.comm.config.ConfigReader;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @Classname RedisConfig
 * @Description RedisConfig
 * @Date 7/7/2022 11:33 AM
 */
public class RedisConfig {

    private RedisClient redisClient;

    private StatefulRedisConnection<String, String> connection;

    private RedisCommands<String, String> redisCommands;

    /**
     * set指令 存活时间3000ms
     */
    public static final SetArgs setArgs = SetArgs.Builder.nx().ex(60*60*24);

    public RedisConfig() {
        // 初始化Lettuce客户端
        String redisHost = ConfigReader.getComplexConfigString("redis.host");
        Integer redisPort = ConfigReader.getComplexConfigInteger("redis.port");
        RedisURI redisURI = RedisURI.builder()
                .withHost(redisHost)
                .withPort(redisPort)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
        this.redisClient = RedisClient.create(redisURI);
        this.connection = this.redisClient.connect();
        this.redisCommands = this.connection.sync();
    }

    public RedisClient getRedisClient() {
        return redisClient;
    }

    public void setRedisClient(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    public StatefulRedisConnection<String, String> getConnection() {
        return connection;
    }

    public void setConnection(StatefulRedisConnection<String, String> connection) {
        this.connection = connection;
    }

    public RedisCommands<String, String> getRedisCommands() {
        return redisCommands;
    }

    public void setRedisCommands(RedisCommands<String, String> redisCommands) {
        this.redisCommands = redisCommands;
    }
}
