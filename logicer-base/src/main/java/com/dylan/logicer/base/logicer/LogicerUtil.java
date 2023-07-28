package com.dylan.logicer.base.logicer;

import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;

/**
 * @Classname LogicerUtil
 * @Description Logicer协议工具类
 * @Date 4/28/2022 10:21 AM
 */
public class LogicerUtil {

    private static final MyLogger logger = MyLoggerFactory.getLogger(LogicerUtil.class);


    /**
     * 根据报文内容和目的用户组装Logicer报文
     * @param realMsg
     * @param toUser
     * @return
     */
    public static String getPackagedLogicerMessage(String realMsg, String toUser){
        return realMsg + "<@" + toUser + ">";
    }


    public static boolean isStandardLogicerContent(String str){
        if (!str.contains(LogicerConstant.LOGICER_CONTENT_STR)){
            return false;
        }
        String[] split = str.split(LogicerConstant.LOGICER_CONTENT_STR);
        return split.length == 2;
    }

    /**
     * 是否为用户名@密码的形式
     * @param str
     * @return
     */
    public static boolean isLoginStr(String str){
        if (!str.contains("@")){
            return false;
        }
        String[] split = str.split("@");
        return StringUtils.isNotBlank(split[0]) && StringUtils.isNotBlank(split[1]) && split.length == 2;
    }
    /**
     * 将字符串转化为ExitContent
     * @param str
     * @return
     */
    public static LogicerContent getExitContentFromStr(String str){
        if (!str.contains(LogicerConstant.LOGICER_CONTENT_STR)){
            str = LogicerConstant.LOGICER_MSG_START_STR + str;
        }
        if (str.endsWith(LogicerConstant.LOGICER_CONTENT_STR)){
            str = str + " ";
        }
        String[] split = str.split(LogicerConstant.LOGICER_CONTENT_STR);
        return new LogicerContent(split[0], split[1]);
    }

    /**
     * 将字符串转化为LogicerContent
     * @param str
     * @return
     */
    public static LogicerContent getLogicerContentFromStr(String str){
        String content = str.contains(LogicerConstant.LOGICER_MSG_START_STR)
                ? str : LogicerConstant.LOGICER_MSG_START_STR + str;
        if (content.endsWith(LogicerConstant.LOGICER_CONTENT_STR)){
            content = content + " ";
        }
        String[] split = content.split(LogicerConstant.LOGICER_CONTENT_STR);
        return new LogicerContent(split[0], split[1]);
    }

    /**
     * 将字符串转化为LogicerContent
     * @param str
     * @return
     */
    public static LogicerContent getCommandContentFromStr(String str){
        if (!str.contains(LogicerConstant.LOGICER_CONTENT_STR)){
            str = LogicerConstant.COMMAND_MSG_START_STR + str;
        }
        if (str.endsWith(LogicerConstant.LOGICER_CONTENT_STR)){
            str = str + " ";
        }
        String[] split = str.split(LogicerConstant.LOGICER_CONTENT_STR);
        String commandStr = split[1];
        if (!commandStr.contains("_")){
            return null;
        }
        // 1_dylan_logicer_Create Team
        // COMMAND的类型_目标用户名_目标群名_命令描述
        String[] commands = commandStr.split("_");
        if (commands.length < 4){
            return null;
        }
        LogicerCommandWord logicerCommandWord = new LogicerCommandWord("", commands[1], commands[2], commands[3]);
        try {
            return new LogicerContent(commands[0], LogicerConstant.COMMON_OBJECT_MAPPER.writeValueAsString(logicerCommandWord));
        } catch (JsonProcessingException e) {
            logger.error("Error writeValueAsString for {}", logicerCommandWord);
            logger.error("Error msg : {}", e.getMessage());
            return null;
        }
    }

    /**
     * 将字符串构建为Talk类型的LogicerContent
     * @param msg
     * @return
     */
    public static LogicerContent getTalkContentFromStr(String msg) {
        LogicerContent talkContent = new LogicerContent();
        talkContent.setActionName("TALK");
        // 暂不支持将复杂字符串解析
        talkContent.setActionWord(msg);
        return talkContent;
    }

    public static LogicerContent getHeartBeatContentFromStr(String msg) {
        LogicerContent heartContent = new LogicerContent();
        heartContent.setActionName("HEARTBEAT");
        // 暂不支持复杂字符串解析
        heartContent.setActionWord(msg);
        return heartContent;
    }
}
