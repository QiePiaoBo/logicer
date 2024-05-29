package com.dylan.chat.ws;

import com.dylan.chat.client.LogicerNettyClientBuildService;
import com.dylan.chat.client.LogicerNettyClientUtil;
import com.dylan.chat.config.ConversationUtil;
import com.dylan.chat.config.WebsocketConstant;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.dylan.logicer.base.logicer.LogicerUtil;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import jakarta.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @Classname MyWebSocketHandler
 * @Description MyWebSocketHandler
 * @Date 7/6/2022 3:55 PM
 */
@Service
public class MyWebSocketHandler extends TextWebSocketHandler {

    private static final MyLogger logger = MyLoggerFactory.getLogger(MyWebSocketHandler.class);

    @Resource
    private LogicerNettyClientBuildService logicerNettyClientBuildService;

    /**
     * 在线人数+1
     */
    public static void addOnlineCount(){
        WebsocketConstant.WS_ONLINE_NUM.incrementAndGet();
    }

    /**
     * 在线人数减一
     */
    public static void subOnlineCount(){
        WebsocketConstant.WS_ONLINE_NUM.decrementAndGet();
    }


    /**
     * 接受客户端消息并发送到Netty服务器
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) throws Exception {
        String messagePayload = message.getPayload();
        String userName = getSessionProperty(session, WebsocketConstant.WS_PROPERTIES_USERNAME);
        String sessionId = getSessionProperty(session, WebsocketConstant.WS_PROPERTIES_SESSIONID);
        String talkWith = getSessionProperty(session, WebsocketConstant.WS_PROPERTIES_TALKWITH);
        String msgAreaType = getSessionProperty(session, WebsocketConstant.WS_PROPERTIES_MSG_AREA_TYPE);
        String completeMsg = WebSocketUtil.getCompleteMsg(messagePayload);
        logger.info("handling textMessage ---> {}&{}: {}【{}】", userName, sessionId, messagePayload, completeMsg);
        if (Objects.isNull(completeMsg)){
            return;
        }
        // 将接收到的报文发送给netty服务
        if (LogicerUtil.isLoginStr(completeMsg)){
            String[] split = completeMsg.split("@");
            // 创建netty客户端并将本条报文透传到netty服务
            if (completeMsg.contains(userName + "@") && split.length == 2){
                // 构建客户端时 只需用户名、密码(自动重连待完成)、sessionId、消息类型即可，服务端可以根据这些参数和消息体完成消息的推送
                logicerNettyClientBuildService.startConnection(userName, split[1], talkWith, sessionId, msgAreaType);
            }
        }else {
            // 发送消息
            LogicerNettyClientUtil.sendMessage(getWsConversationMapKey(session), messagePayload);
        }
    }

    /**
     * 从session中获取目的属性值
     * @param session
     * @param aimKey
     * @return
     */
    private String getSessionProperty(@NonNull WebSocketSession session, String aimKey) {
        Object websocket_property = session.getAttributes().get(aimKey);
        String property = "";
        if (Objects.nonNull(websocket_property) && websocket_property instanceof String){
            property = (String) websocket_property;
        }
        // logger.info("UserName is {}", userName);
        return property;
    }

    /**
     * 连接建立时
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        // 从 session 中获取参数
        Map<String, Object> attributes = session.getAttributes();
        String curUser = (String) attributes.get("curUser");
        String aimUser = (String) attributes.get("aimUser");
        String chatType = (String) attributes.get("chatType");
        logger.info("curUser " + curUser + " aimUser " + aimUser + " chatType " + chatType);
        // logger.info("UserName is {}", userName);
        WebSocketSession put = WebsocketConstant.WS_SESSION_POOL.put(getWsConversationMapKey(session), session);
        if (Objects.isNull(put)){
            addOnlineCount();
        }
        session.sendMessage(new TextMessage("请输入用户名@密码以登录，示例：lingling@123456"));
    }

    /**
     * 连接关闭后
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        String sessionId = session.getId();
        String mapKey = getWsConversationMapKey(session);
        if (WebsocketConstant.WS_SESSION_POOL.containsKey(mapKey)){
            WebsocketConstant.WS_SESSION_POOL.remove(mapKey);
            subOnlineCount();
            // Ws连接结束时 netty客户端也要断开
            LogicerNettyClientUtil.userLogout(mapKey);
        }
        logger.info("{} disconnect!", sessionId);
    }

    /**
     * 从WebSocketSession中获取当前会话的属性
     * @param session
     * @return
     */
    private String getWsConversationMapKey(WebSocketSession session) {
        Object websocket_username = session.getAttributes().get(WebsocketConstant.WS_PROPERTIES_USERNAME);
        Object websocket_talkWith = session.getAttributes().get(WebsocketConstant.WS_PROPERTIES_TALKWITH);
        String userName = "";
        String talkWith = "";
        if (Objects.nonNull(websocket_username) && websocket_username instanceof String){
            userName = (String) websocket_username;
        }
        if (Objects.nonNull(websocket_talkWith) && websocket_talkWith instanceof String){
            talkWith = (String) websocket_talkWith;
        }
        // 如果无法获取属性 关闭连接
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(talkWith)){
            logger.error("ERROR, param not valid: {}", session.getAttributes());
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ConversationUtil.getConversationMapKey(userName, talkWith);
    }

}
