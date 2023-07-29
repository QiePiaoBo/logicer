package com.dylan.chat.config;

import com.dylan.chat.dal.entity.TeamEntity;
import com.dylan.chat.service.SessionService;
import com.dylan.chat.service.TeamService;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
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
    private SessionService sessionService;

    @Resource
    private TeamService teamService;

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
        // 使用WS的子协议传入连接参数 结构示意: userName,talkWith
        String authorization = serverHttpRequest.getServletRequest().getHeader("Sec-WebSocket-Protocol");
        if (StringUtils.isBlank(authorization)){
            return false;
        }
        HttpSession httpSession = serverHttpRequest.getServletRequest().getSession(true);
        if (Objects.nonNull(httpSession)){
            String userName = (String) httpSession.getAttribute("SESSION_USERNAME");
            if (StringUtils.isBlank(userName)){
                userName = authorization;
            }
        }
        if (authorization.contains(",")){
            String[] split = authorization.split(",");
            if (split.length < 3){
                // 参数异常 拒绝WS客户端进行握手
                return false;
            }
            String fromUser = split[0].trim();
            String aimUserOrGroup = split[1].trim();
            String msgAreaType = split[2].trim();
            // 初始化sessionId
            Integer sessionId = null;
            TeamEntity teamEntity = null;
            if ("0".equals(msgAreaType)){
                // 首先获取用户名Id映射(这里是作为Netty核心服务的客户端，所以要将用户名转Id的动作前置以保证Netty核心服务的处理速度)
                // todo 完成客户端基本功能 将所有name转Id的动作前置以优化服务处理速度
                Map<String, Integer> userNameIdMap = sessionService.getUserNameIdMap(Arrays.asList(fromUser, aimUserOrGroup));
                if (userNameIdMap.size() == 0){
                    logger.error("<beforeHandshake> error, error getting session of {} and {}", split[0], split[1]);
                    return false;
                }
                // 点对点消息 为发起用户和目的用户获取会话Id
                sessionId = sessionService.getOrCreateSessionForUser(userNameIdMap.getOrDefault(fromUser,null),
                        userNameIdMap.getOrDefault(aimUserOrGroup, null));
                if (Objects.isNull(sessionId)){
                    logger.error("<beforeHandshake> error, error getting session of {} and {}", split[0], split[1]);
                    return false;
                }
            } else if ("1".equals(msgAreaType)){
                // 群消息 为发起用户和群获取会话Id
                teamEntity = teamService.getTeamByTeamName(aimUserOrGroup);
                if (Objects.isNull(teamEntity)){
                    logger.error("<beforeHandshake> error, error getting team of {} and {}", split[0], split[1]);
                    return false;
                }
                // 点对群消息 获取到群之后 目的群名改为目的群Id
                aimUserOrGroup = teamEntity.getId() + "";
                sessionId = sessionService.getOrCreateSessionForTeam(fromUser, teamEntity.getId());
                if (Objects.isNull(sessionId)){
                    logger.error("<beforeHandshake> error, error getting session of {} and {}", split[0], split[1]);
                    return false;
                }
            }
            map.put(WebsocketConstant.WS_PROPERTIES_USERNAME, fromUser);
            map.put(WebsocketConstant.WS_PROPERTIES_TALKWITH, aimUserOrGroup);
            map.put(WebsocketConstant.WS_PROPERTIES_MSG_AREA_TYPE, msgAreaType);
            map.put(WebsocketConstant.WS_PROPERTIES_SESSIONID, sessionId + "");
            // 如果传入了两个子协议 必须返回其中一个给客户端表示服务端选择了其中一个 如果将两个子协议原样返回 会导致连接失败
            serverHttpResponse.getServletResponse().setHeader("Sec-WebSocket-Protocol", split[0]);
        }else {
            serverHttpResponse.getServletResponse().setHeader("Sec-WebSocket-Protocol", authorization);
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
