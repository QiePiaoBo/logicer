package com.dylan.comm.config;

import com.dylan.comm.comp.CompManager;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.Yaml;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Classname ConfigReader
 * @Description 从yml文件中读取配置信息的工具类
 * @Date 2022/3/15 14:43
 */
public class ConfigReader {

    static MyLogger logger = MyLoggerFactory.getLogger(ConfigReader.class);
    public static Map<String, Map<String, Object>> configMaps = new HashMap<>();

    /**
     * 获取一个文件中的所有配置内容
     *
     * @param ymlName
     * @return
     */
    public static Map<String, Object> getConfigFromYml(String ymlName) {
        Map<String, Object> config = null;
        try {
            logger.info("Reading config from: {}", ymlName);
            // 如果map中已经存在该配置文件的配置就直接返回
            if (Objects.nonNull(configMaps.getOrDefault(ymlName, null))) {
                return configMaps.get(ymlName);
            }
            Yaml yaml = new Yaml();
            InputStream resourceAsStream = ConfigReader.class.getClassLoader().getResourceAsStream(ymlName);
            config = yaml.load(resourceAsStream);
            // 如果取出的结果不为空就放到Map中去
            if (Objects.nonNull(config) && !config.isEmpty()) {
                configMaps.put(ymlName, config);
            }
            return config;
        } catch (Exception e) {
            logger.error("Error, ", e.getMessage());
        }
        return config;
    }

    /**
     * 获取一层配置文件中的配置信息
     *
     * @param configName
     * @return
     */
    public static Object getConfig(String configName) {
        Collection<Map<String, Object>> values = configMaps.values();
        for (Map<String, Object> m : values) {
            if (m.containsKey(configName)) {
                return m.get(configName);
            }
        }
        return null;
    }

    /**
     * 能够精确确定到配置的最后一级，结果为字符串类型
     *
     * @param configName
     * @return
     */
    public static String getComplexConfigString(String configName) {
        Object complexConfig = getComplexConfig(configName);
        if (complexConfig instanceof String) {
            return (String) complexConfig;
        }
        if (complexConfig instanceof Integer){
            return complexConfig + "";
        }
        // 如果到了这里，说明所查询的配置并非最后一级，报出错误并返回默认值
        logger.error("Error, the config you want : {} is not last stage.", configName);
        return "DEFAULT_ERROR_CONFIG";
    }

    /**
     * 能够精确确定到配置的最后一级，结果为字符串类型，允许传入默认值
     *
     * @param configName
     * @return
     */
    public static String getComplexConfigStringWithDefault(String configName, String defaultValue) {
        Object complexConfig = getComplexConfig(configName);
        if (complexConfig instanceof String) {
            return (String) complexConfig;
        }
        if (complexConfig instanceof Integer){
            return complexConfig + "";
        }
        // 如果到了这里，说明所查询的配置并非最后一级，报出错误并返回默认值
        logger.error("Error, the config you want : {} is not last stage.Return defaultValue: {}", configName, defaultValue);
        return defaultValue;
    }

    /**
     * 能够精确确定到配置的最后一级，结果为字符串类型
     *
     * @param configName
     * @return
     */
    public static Integer getComplexConfigInteger(String configName) {
        Object complexConfig = getComplexConfig(configName);
        if ((complexConfig instanceof Integer)) {
            return (Integer) complexConfig;
        }
        // 如果到了这里，说明所查询的配置并非最后一级，报出错误并返回默认值
        logger.error("Error, the config you want : {} is not last stage.", configName);
        return -1;
    }


    /**
     * 能够精确确定到配置的最后一级，结果为字符串类型，允许传入默认值
     *
     * @param configName
     * @return
     */
    public static Integer getComplexConfigIntegerWithDefault(String configName, Integer defaultValue) {
        Object complexConfig = getComplexConfig(configName);
        if ((complexConfig instanceof Integer)) {
            return (Integer) complexConfig;
        }
        // 如果到了这里，说明所查询的配置并非最后一级，报出错误并返回默认值
        logger.error("Error, the config you want : {} is not last stage. Return defaultValue: {}", configName, defaultValue);
        return defaultValue;
    }

    /**
     * 获取多级配置文件的配置, 不一定查到了最后一级
     *
     * @param configName
     * @return
     */
    private static Object getComplexConfig(String configName) {
        List<String> configGrades = new ArrayList<>();
        if (configName.contains(".")) {
            String[] split = configName.split("\\.");
            configGrades.addAll(Arrays.asList(split));
        }
        Object temp = null;
        for (int i = 0; i < configGrades.size(); i++) {
            if (Objects.nonNull(temp)) {
                if (temp instanceof Map) {
                    temp = ((Map<?, ?>) temp).getOrDefault(configGrades.get(i), null);
                    continue;
                }
            }
            temp = getConfig(configGrades.get(i));
            if (Objects.isNull(temp)) {
                return null;
            }
        }
        return temp;
    }

    /**
     * 未查到最后一级时 结果应该解析为Map
     *
     * @param configName
     * @return
     */
    public static Map getComplexConfigMap(String configName) {
        Object complexConfig = getComplexConfig(configName);
        Map complexConfigMap = null;
        if (complexConfig instanceof HashMap) {
            ObjectMapper objectMapper = CompManager.jackson_object_mapper;
            try {
                complexConfigMap = objectMapper.readValue(objectMapper.writeValueAsBytes(complexConfig), Map.class);
            } catch (IOException e) {
                logger.error("Parsing object : {} to map error, please check your configuration: {}", complexConfig, configName);
            }
        }
        return complexConfigMap;
    }

}
