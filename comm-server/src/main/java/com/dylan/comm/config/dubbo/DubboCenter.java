package com.dylan.comm.config.dubbo;

import com.dylan.comm.config.ConfigReader;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.config.configcenter.DynamicConfiguration;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConsumerConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname DubboUtil
 * @Description DubboUtil
 * @Date 8/1/2023 2:29 PM
 */
public class DubboCenter {

    private static final MyLogger logger = MyLoggerFactory.getLogger(DubboCenter.class);

    private static final Map<Class<?>, ReferenceConfig<?>> referenceConfigMap = new ConcurrentHashMap<>();

    /**
     * 获取Dubbo启动器实例
     *
     * @return
     */
    private static <T> ReferenceConfig<T> getReference(Class<T> serviceClass) {
        // 读取配置
        String dubboRegistryAddress = ConfigReader.getComplexConfigStringWithDefault("dubbo.registry-address", null);
        String dubboAppName = ConfigReader.getComplexConfigStringWithDefault("dubbo.application-name", null);
        if (StringUtils.isBlank(dubboAppName) || StringUtils.isBlank(dubboRegistryAddress)) {
            logger.error("Error, cannot get config for dubbo, registry-address: {}, application-name: {}", dubboRegistryAddress, dubboAppName);
            return null;
        }
        // application信息
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(dubboAppName);
        // registry信息
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(dubboRegistryAddress);
        registryConfig.setPassword("19970413");
        registryConfig.setUsername("dylan");
        registryConfig.setRegister(false);
        Map<String, String> map = new HashMap<>();
        map.put("namespace", "a4064324-ce85-4574-bdd5-89373f6ee0a4");
        registryConfig.setParameters(map);

        ReferenceConfig<T> reference = new ReferenceConfig<>();
        reference.setRegistry(registryConfig);
        reference.setApplication(applicationConfig);
        reference.setInterface(serviceClass);
        return reference;
    }

    /**
     * 根据class 获取DubboReference实例
     *
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T getService(Class<T> serviceClass) {
        ReferenceConfig<T> reference;
        if (referenceConfigMap.containsKey(serviceClass)) {
            reference = (ReferenceConfig<T>) referenceConfigMap.get(serviceClass);
        } else {
            reference = getReference(serviceClass);
        }
        if (Objects.isNull(reference)) {
            logger.error("Error getting instance for class : {}", serviceClass);
            throw new RuntimeException("Error getting instance for class : " + serviceClass.getName());
        }
        return reference.get();
    }

}
