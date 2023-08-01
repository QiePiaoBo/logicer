package com.dylan.comm.config;

import java.util.Objects;

/**
 * @Classname CommonConfig
 * @Description 通用配置
 * @Date 2022/3/15 14:50
 */
public class MyCoreConfig {


    /**
     * 主动日志的前缀
     */
    public static final String LOGGER_HEADER = ">>>>>> ";

    /**
     * 默认的service名称
     */
    public static final String NULL_SERVICE_NAME = "NULL_SERVICE_NAME";

    /**
     * 配置文件中的主服务端口号
     */
    public static final Integer NETTY_SERVER_PORT =
            Objects.equals(ConfigReader.getComplexConfigInteger("server.port"), -1)
                    ? 9999
                    : (ConfigReader.getComplexConfigInteger("server.port"));

    /**
     * 主配置文件的地址
     */
    public static final String BASE_YML = "app.yml";

    /**
     * 启动类所在的的包名，过滤掉artifactId中的 “-”
     */
    public static final String BASE_PACKAGE = ConfigReader.getComplexConfigString("app.groupId")
            + "."
            + ConfigReader.getComplexConfigString("app.artifactId").replaceAll("-", "");

}
