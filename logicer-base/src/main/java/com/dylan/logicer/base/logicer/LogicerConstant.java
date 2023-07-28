package com.dylan.logicer.base.logicer;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

/**
 * @author Dylan
 * @Date : 2022/4/16 - 17:25
 */
public class LogicerConstant implements Serializable {


    public static final ObjectMapper COMMON_OBJECT_MAPPER = new ObjectMapper();

    public static final Integer LOGICER_VERSION = 1;

    public static final Integer MAX_DISCONNECT_TIME = 5;

    public static final String DEFAULT_SESSION_ID = "DEFAULT_SESSION_ID";

    /**
     * 服务端向客户端请求确认状态时的字符串
     */
    public static final String LOGICER_STATE_ASK = "What's wrong with you man?";

    /**
     * 客户端回复服务端的确认请求时的字符串
     */
    public static final String LOGICER_STATE_ANSWER = "Fine, keep me please.";

    /**
     * 服务端要求登录
     */
    public static final String ASK_FOR_LOGIN_INFO = LogicerConstant.LOGICER_MSG_START_STR +"PLEASE_LOGIN";

    /**
     * logicer_client的标志 【actionName : actionWord】
     */
    public static final String LOGICER_CONTENT_STR = " : ";

    /**
     * 普通消息体的开头
     */
    public static final String LOGICER_MSG_START_STR = "LOGICER" + LogicerConstant.LOGICER_CONTENT_STR;

    /**
     * COMMAND类型消息体的开头
     */
    public static final String COMMAND_MSG_START_STR = "COMMAND" + LogicerConstant.LOGICER_CONTENT_STR;

    /**
     * HEARTBEAT类型消息体的开头
     */
    public static final String HEARTBEAT_MSG_START_STR = "HEARTBEAT" + LogicerConstant.LOGICER_CONTENT_STR;

    /**
     * TALK类型消息体的开头
     */
    public static final String TALK_MSG_START_STR = "TALK" + LogicerConstant.LOGICER_CONTENT_STR;

    /**
     * 登录消息体的开头
     */
    public static final String LOGIN_MSG_START_STR = "LOGIN" + LogicerConstant.LOGICER_CONTENT_STR;

    /**
     * 客户端主动与服务端断开连接 客户端与服务端都删除该会话
     */
    public static final String LOGICER_CLIENT_EXIT = "EXIT" + LogicerConstant.LOGICER_CONTENT_STR + "BYE";

    /**
     * 指定接收者为全体成员
     */
    public static final String MESSAGE_AT_ALL = "@all";

    public static enum GroupEnum{

        ERROR_USER (-1, "SUPER_USER"),
        SUPER_USER (0, "SUPER_USER"),
        MANAGER (1, "MANAGER"),
        COMMON_USER (2, "COMMON_USER")
        ;

        private final Integer groupCode;
        private final String groupName;


        GroupEnum(Integer groupCode, String groupName) {
            this.groupCode = groupCode;
            this.groupName = groupName;
        }

        public Integer getGroupCode() {
            return groupCode;
        }

        public String getGroupName() {
            return groupName;
        }

        /**
         * 根据GroupCode获取GroupEnum
         * @param groupCode
         * @return
         */
        public static GroupEnum getByGroupCode(Integer groupCode){
            switch (groupCode){
                case 0:
                    return GroupEnum.SUPER_USER;
                case 1:
                    return GroupEnum.MANAGER;
                case 2:
                    return GroupEnum.COMMON_USER;
                default:
                    return GroupEnum.ERROR_USER;
            }
        }
    }

    /**
     * Command指令枚举
     */
    public static enum CommandOperationEnum{
        DEFAULT_OPERATION ("DEFAULT_OPERATION", -1),
        /**
         * 创建群
         */
        CREATE_TEAM  ("CREATE_TEAM", 0),
        /**
         * 邀请人进群
         */
        INVITE_MEMBER  ("INVITE_MEMBER", 1),
        /**
         * 踢人
         */
        KICK_MEMBER  ("KICK_MEMBER", 2),
        /**
         * 删除群
         */
        DELETE_TEAM  ("DELETE_TEAM", 3),
        /**
         * 添加管理员
         */
        ADD_MANAGER  ("ADD_MANAGER", 4),
        /**
         * 删除用户管理员权限
         */
        DELETE_MANAGER  ("DELETE_MANAGER", 5),

        /**
         * 将其他人的会话关闭
         */
        CLEAN_OTHER_USERS ("CLEAN_OTHER_USERS", 6);

        private final Integer operationCode;
        private final String operationName;

        CommandOperationEnum(String operationName, Integer operationCode) {
            this.operationCode = operationCode;
            this.operationName = operationName;
        }
        public Integer getOperationCode() {
            return operationCode;
        }

        public String getOperationName() {
            return operationName;
        }

        /**
         * 根据Code获取对应的Command指令枚举
         * @param operationCode
         * @return
         */
        public static CommandOperationEnum getEnumByCode(Integer operationCode){
            switch (operationCode){
                case 0:
                    return CommandOperationEnum.CREATE_TEAM;
                case 1:
                    return CommandOperationEnum.INVITE_MEMBER;
                case 2:
                    return CommandOperationEnum.KICK_MEMBER;
                case 3:
                    return CommandOperationEnum.DELETE_TEAM;
                case 4:
                    return CommandOperationEnum.ADD_MANAGER;
                case 5:
                    return CommandOperationEnum.DELETE_MANAGER;
                case 6:
                    return CommandOperationEnum.CLEAN_OTHER_USERS;
                default:
                    return CommandOperationEnum.DEFAULT_OPERATION;
            }
        }
    }

}
