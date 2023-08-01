package com.dylan.comm.action;

import com.dylan.comm.anno.MyService;
import com.dylan.comm.comp.CompManager;
import com.dylan.comm.config.ConfigReader;
import com.dylan.comm.config.MyCoreConfig;
import com.dylan.logicer.base.util.ClassScanner;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Dylan
 * @Date : 2022/4/13 - 15:43
 */
public class ConfigAction {

    /**
     * 将配置文件中的配置信息加载到JVM中
     */
    public static void initConfigurations() {
        // 基于主配置文件，将所有的配置信息加载到CommonConfig
        Map<String, Object> configFromYml = ConfigReader.getConfigFromYml(MyCoreConfig.BASE_YML);
        Object config = configFromYml.getOrDefault("configs", null);
        List<String> configs = new ArrayList<>();
        if (config instanceof List) {
            configs = (List<String>) configFromYml.getOrDefault("configs", null);
        }
        if (!configs.isEmpty()) {
            configs.forEach(ConfigReader::getConfigFromYml);
        }
        // 将所有的加了MyService注解的类添加到CommonConfig中的Map中
        try {
            String servicePackage = MyCoreConfig.BASE_PACKAGE + ".dao.service";
            List<Class<?>> myServices = ClassScanner.getAnnotatedClassesForPackage(servicePackage, MyService.class);
            for (Class clazz : myServices){
                Annotation annotation = clazz.getAnnotation(MyService.class);
                MyService myService = (MyService) annotation;
                String serviceName = myService.value();
                if (MyCoreConfig.NULL_SERVICE_NAME.equals(myService.value())){
                    serviceName = ClassScanner.lowerFirst(clazz.getSimpleName());
                }
                CompManager.serviceName_myService_mapper.put(serviceName, clazz.newInstance());
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
