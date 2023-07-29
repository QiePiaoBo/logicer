package com.dylan.chat.ws;

import com.dylan.chat.config.ConversationUtil;
import com.dylan.chat.config.WebsocketConstant;
import com.dylan.framework.model.exception.MyException;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname WebSocketUtil
 * @Description WebSocketUtil
 * @Date 4/27/2023 3:27 PM
 */
public class WebSocketUtil {

    private static final Pattern PATTERN_BETWEEN_TWO_CHARACTER = Pattern.compile("\\<@(.*?)\\>");
    private static final String AIMING_NAME_START_CHARACTER = "<@";

    private static final MyLogger logger = MyLoggerFactory.getLogger(WebSocketUtil.class);

    /**
     * 根据conversationMapKey断开连接
     *
     * @param conversationMapKey
     */
    public static void disconnectForUser(String conversationMapKey) {
        WebSocketSession socketSession = WebsocketConstant.WS_SESSION_POOL.getOrDefault(conversationMapKey, null);
        if (Objects.nonNull(socketSession)) {
            try {
                socketSession.sendMessage(new TextMessage("即将断开连接"));
                socketSession.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        WebsocketConstant.WS_SESSION_POOL.remove(conversationMapKey);
        WebsocketConstant.WS_ONLINE_NUM.decrementAndGet();
    }

    /**
     * 获取报文中要@的人的名字
     *
     * @param input
     * @return
     */
    public static String getMessageAimingUser(String input) {
        Matcher matcher = PATTERN_BETWEEN_TWO_CHARACTER.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * 获取报文中真实的业务内容
     *
     * @param input
     * @return
     */
    public static String getCompleteMsg(String input) {
        if (StringUtils.isBlank(input)){
            return null;
        }
        if (input.contains(AIMING_NAME_START_CHARACTER)) {
            return input.substring(0, input.indexOf(AIMING_NAME_START_CHARACTER));
        }
        return null;
    }

    /**
     * 群发消息
     *
     * @param message
     */
    public static void sendTopic(String message) {
        if (WebsocketConstant.WS_SESSION_POOL.isEmpty()) {
            return;
        }
        for (Map.Entry<String, WebSocketSession> entry : WebsocketConstant.WS_SESSION_POOL.entrySet()) {
            try {
                entry.getValue().sendMessage(new TextMessage(message));
            } catch (IOException e) {
                throw new MyException(e);
            }
        }
    }

    /**
     * 点对点发送 这里是netty客户端返回消息给WS之后 WS给WS的客户端返回的过程 所以需要将fromUser和toUser进行一定的转换
     * 发送者为Server时
     * @param toUserName
     * @param message
     */
    public static void sendToWsClient(String fromUserName, String toUserName, String message) {

        try {
            if (fromUserName.equals("Server")) {
                // 如果toUserName是完整的key 就直接进行发送
                if (toUserName.contains("&->")){
                    WebSocketSession socketSession = WebsocketConstant.WS_SESSION_POOL.getOrDefault(toUserName, null);
                    if (Objects.isNull(socketSession)) {
                        return;
                    }
                    socketSession.sendMessage(new TextMessage(fromUserName + ": " + message));
                }else if (toUserName.equals("@all")){
                    // 如果系统@全体成员 就对所有人发送消息 否则就对指定的人发送消息
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Enumeration<String> keys = WebsocketConstant.WS_SESSION_POOL.keys();
                    while (keys.hasMoreElements()){
                        String s = keys.nextElement();
                        WebSocketUtil.sendToWsClient("Server", s, message);
                    }
                }else {
                    Enumeration<String> keys = WebsocketConstant.WS_SESSION_POOL.keys();
                    while (keys.hasMoreElements()) {
                        String s = keys.nextElement();
                        // todo 这里需要优化 服务端发送的消息会送达每一个toUser发起的会话中 因此会发送很多遍 需要精准确定应该送达的会话 可能需要在前端进行处理
                        if (s.contains(toUserName + "&->")) {
                            WebSocketSession webSocketSession = WebsocketConstant.WS_SESSION_POOL.get(s);
                            webSocketSession.sendMessage(new TextMessage(fromUserName + ": " + message));
                        }
                    }
                }
                return;
            }
            // 消息来自 fromUser 发往 toUser，又因为这里是最后一环，需要将消息送达toUser的会话中，所以应该找到toUser&->fromUser对应的WS连接并发送消息
            String conversationMapKey = ConversationUtil.getConversationMapKey(toUserName, fromUserName);
            WebSocketSession socketSession = WebsocketConstant.WS_SESSION_POOL.getOrDefault(conversationMapKey, null);
            if (Objects.isNull(socketSession)) {
                logger.error("<sendToUser> Error sending msg, aim client not online. {} : {}", fromUserName, message);
                return;
            }
            logger.info("<sendToUser> Sending msg... {} : {}", fromUserName, message);
            socketSession.sendMessage(new TextMessage(fromUserName + ": " + message));
        } catch (IOException e) {
            throw new MyException(e);
        }

    }


}
