package com.dylan.framework.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author Dylan
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext context = null;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.context = applicationContext;
    }

    public static String getProperty(String propertyKey){
        return context.getEnvironment().getProperty(propertyKey, "Undefined");
    }



}
