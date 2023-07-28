package com.dylan.logicer.base.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname MyttyLoggerFactory
 * @Description 封装工具类
 * @Date 2022/3/15 14:52
 */
public class MyLoggerFactory {

    public static MyLogger getLogger(String myLoggerName){
        Logger logger = LoggerFactory.getLogger(myLoggerName);
        return new MyLogger(logger);
    }
    public static MyLogger getLogger(Class clazz){
        Logger logger = LoggerFactory.getLogger(clazz);
        return new MyLogger(logger);
    }
}
