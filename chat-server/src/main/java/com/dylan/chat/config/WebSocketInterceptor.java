package com.dylan.chat.config;

import com.dylan.chat.entity.TeamEntity;
import com.dylan.chat.service.SessionServiceImpl;
import com.dylan.chat.service.TeamServiceImpl;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * @Classname WebSocketInterceptor
 * @Description WebSocketInterceptor
 * @Date 7/6/2022 3:52 PM
 */
@Component
public class WebSocketInterceptor implements HandshakeInterceptor {

    private static final MyLogger logger = MyLoggerFactory.getLogger(WebSocketInterceptor.class);

    @Resource
    private SessionServiceImpl sessionServiceImpl;

    @Resource
    private TeamServiceImpl teamServiceImpl;

    /**
     * 握手前
     * @param request
     * @param response
     * @param webSocketHandler
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
        ServletServerHttpResponse serverHttpResponse = (ServletServerHttpResponse) response;

        String path = request.getURI().getPath();
        String[] pathSegments = path.split("/");

        // Assuming the path is in the format: /chat/{curUser}/{aimUser}/{chatType}
        if (pathSegments.length >= 4) {
            String curUser = pathSegments[2];
            String aimUserOrGroup = pathSegments[3];
            String chatType = pathSegments[4];
            map.put("curUser", curUser);
            map.put("aimUser", aimUserOrGroup);
            map.put("chatType", chatType);

            // 初始化sessionId
            Integer sessionId = null;
            TeamEntity teamEntity = null;
            if ("0".equals(chatType)){
                // 首先获取用户名Id映射(这里是作为Netty核心服务的客户端，所以要将用户名转Id的动作前置以保证Netty核心服务的处理速度)
                // todo 完成客户端基本功能 将所有name转Id的动作前置以优化服务处理速度
                Map<String, Integer> userNameIdMap = sessionServiceImpl.getUserNameIdMap(Arrays.asList(curUser, aimUserOrGroup));
                if (userNameIdMap.size() == 0){
                    logger.error("<beforeHandshake> error, error getting userNameIdMap of {} and {}", curUser, aimUserOrGroup);
                    return false;
                }
                // 点对点消息 为发起用户和目的用户获取会话Id
                sessionId = sessionServiceImpl.getOrCreateSessionForUser(userNameIdMap.getOrDefault(curUser,null),
                        userNameIdMap.getOrDefault(aimUserOrGroup, null));
                if (Objects.isNull(sessionId)){
                    logger.error("<beforeHandshake> error, error getting session of {} and {}", curUser, aimUserOrGroup);
                    return false;
                }
            } else if ("1".equals(chatType)){
                // 群消息 为发起用户和群获取会话Id
                teamEntity = teamServiceImpl.getTeamByTeamName(aimUserOrGroup);
                if (Objects.isNull(teamEntity)){
                    logger.error("<beforeHandshake> error, error getting team of {} and {}", curUser, aimUserOrGroup);
                    return false;
                }
                // 点对群消息 获取到群之后 目的群名改为目的群Id
                aimUserOrGroup = teamEntity.getId() + "";
                sessionId = sessionServiceImpl.getOrCreateSessionForTeam(curUser, teamEntity.getId());
                if (Objects.isNull(sessionId)){
                    logger.error("<beforeHandshake> error, error getting session of {} and {}", curUser, aimUserOrGroup);
                    return false;
                }
            }
            map.put(WebsocketConstant.WS_PROPERTIES_USERNAME, curUser);
            map.put(WebsocketConstant.WS_PROPERTIES_TALKWITH, aimUserOrGroup);
            map.put(WebsocketConstant.WS_PROPERTIES_MSG_AREA_TYPE, chatType);
            map.put(WebsocketConstant.WS_PROPERTIES_SESSIONID, sessionId + "");

        }else {
            return false;
        }
        logger.info("start shaking hands >>>>>>");
        return true;
    }

    /**
     * 握手后
     * @param request
     * @param response
     * @param webSocketHandler
     * @param e
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler webSocketHandler, Exception e) {
        logger.info("handshake success.");
    }


}
