package com.dylan.chat.config;

import java.util.Set;

/**
 * @Classname ConversationUtil
 * @Description ConversationUtil
 * @Date 7/7/2023 2:57 PM
 */
public class ConversationUtil {

    /**
     * 组装会话发起者与会话接收者字符串 作为一些映射关系的Key 注意 如果是点对点消息 两个入参是两个字符串 如果是点对群消息 第二个入参应该是数字类型字符串
     * @param startUser
     * @param talkWith
     * @return
     */
    public static String getConversationMapKey(String startUser, String talkWith){
        return startUser + "&->" + talkWith;
    }

    /**
     * 判断该用户是否有其他的WS连接在使用nettyClient
     * @param userNames
     * @param conversationMapKey
     * @return
     */
    public static boolean hasMoreNettyClientForUser(Set<String> userNames, String conversationMapKey){
        String aimName = conversationMapKey.substring(0, conversationMapKey.indexOf("&->"));
        int total = 1;
        for (String name : userNames){
            if (name.startsWith(aimName)){
                total += 1;
            }
        }
        return total > 1;
    }

}
