package com.dylan.logicer.base.logger;

import org.slf4j.Logger;

/**
 * @Classname MyLogger
 * @Description 封装自定义Logger
 * @Date 2022/3/15 14:53
 */
public class MyLogger {

    private final Logger logger;
    
    private final String LOGGER_HEADER = ">>>>>> ";

    public MyLogger(Logger logger) {
        this.logger = logger;
    }

    public void info(String msg){
        if (logger.isInfoEnabled()){
            logger.info(LOGGER_HEADER + msg);
        }
    }

    public void info(String msg, Object object){
        if (logger.isInfoEnabled()){
            logger.info(LOGGER_HEADER + msg, object);
        }
    }

    public void info(String msg, Object... objects){
        if (logger.isInfoEnabled()){
            logger.info(LOGGER_HEADER + msg, objects);
        }
    }

    public void info(String msg, Throwable throwable){
        if (logger.isInfoEnabled()){
            logger.info(LOGGER_HEADER + msg, throwable);
        }
    }

    public void error(String msg, Object... objects){
        if (logger.isErrorEnabled()){
            logger.error(LOGGER_HEADER + msg, objects);
        }
    }

    public void error(String msg){
        if (logger.isErrorEnabled()){
            logger.error(LOGGER_HEADER + msg);
        }
    }

    public void error(String msg, Throwable throwable){
        if (logger.isErrorEnabled()){
            logger.error(LOGGER_HEADER + msg, throwable);
        }
    }

    public void error(String msg, Object object){
        if (logger.isErrorEnabled()){
            logger.error(LOGGER_HEADER + msg, object);
        }
    }

    public void debug(String msg, Object... objects){
        if (logger.isDebugEnabled()){
            logger.debug(LOGGER_HEADER + msg, objects);
        }
    }

    public void debug(String msg){
        if (logger.isDebugEnabled()){
            logger.debug(LOGGER_HEADER + msg);
        }
    }

    public void debug(String msg, Throwable throwable){
        if (logger.isDebugEnabled()){
            logger.debug(LOGGER_HEADER + msg, throwable);
        }
    }

    public void debug(String msg, Object object){
        if (logger.isDebugEnabled()){
            logger.debug(LOGGER_HEADER + msg, object);
        }
    }

}
