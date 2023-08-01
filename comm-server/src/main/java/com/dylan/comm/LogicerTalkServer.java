package com.dylan.comm;

import com.dylan.comm.action.ConfigAction;
import com.dylan.comm.action.ServerAction;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;

/**
 * @author Dylan
 * @Date : 2022/3/13 - 4:24
 * @Description : 主启动类
 * @Function :
 */
public class LogicerTalkServer {

    static MyLogger logger = MyLoggerFactory.getLogger(LogicerTalkServer.class);

    public static void main(String[] args) {
        ConfigAction.initConfigurations();
        ServerAction.prepareMQEnv();
        ServerAction.openNettyServer();
    }
}