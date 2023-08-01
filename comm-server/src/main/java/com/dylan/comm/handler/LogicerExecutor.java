package com.dylan.comm.handler;

import com.dylan.comm.comp.CompManager;
import com.dylan.comm.dao.entity.User;
import com.dylan.comm.dao.service.UserService;
import com.dylan.comm.util.LogicerMessageUtil;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.dylan.logicer.base.logicer.LogicerCommandWord;
import com.dylan.logicer.base.logicer.LogicerConstant;
import com.dylan.logicer.base.logicer.LogicerContent;
import com.dylan.logicer.base.logicer.LogicerHeader;
import com.dylan.logicer.base.logicer.LogicerMessage;
import com.dylan.logicer.base.logicer.LogicerMessageBuilder;
import com.dylan.logicer.base.logicer.LogicerSubProtocol;
import com.dylan.logicer.base.logicer.LogicerTalkWord;
import com.dylan.logicer.base.logicer.LogicerUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import org.apache.commons.lang3.StringUtils;
import java.net.SocketAddress;
import java.util.Objects;
import java.util.Set;

/**
 * @Classname LogicerExecutor
 * @Description LogicerTalk服务的消息处理中心
 * @Date 4/29/2022 3:43 PM
 */
public class LogicerExecutor {

    private static final MyLogger logger = MyLoggerFactory.getLogger(LogicerExecutor.class);

    public LogicerExecutor() {
    }

    /**
     * 处理logicer协议的消息
     * @param channel
     * @param logicerMessage
     * @throws JsonProcessingException
     */
    public void executeMessage(Channel channel, LogicerMessage logicerMessage) throws JsonProcessingException {
        // 该连接中对方服务器的地址
        SocketAddress socketAddress = channel.remoteAddress();
        LogicerHeader logicerHeader = logicerMessage.getLogicerHeader();
        LogicerSubProtocol subProtocol = logicerHeader.getLogicerProtocol();
        // 根据请求头中的子协议来对报文进行不同的处理
        // logicer子协议的消息为第一优先级
        if (LogicerSubProtocol.LOGICER.equals(subProtocol)) {
            this.executeLogicerMessage(logicerMessage, socketAddress);
        }
        // 心跳子协议的消息默认放开
        if (LogicerSubProtocol.HEARTBEAT.equals(subProtocol)){
            this.executeHeartBeatMessage(logicerMessage, socketAddress);
        }else {
            // talk与command类型的消息都需要判断登录状态
            if (checkLoginStatus(socketAddress)){
                if (LogicerSubProtocol.TALK.equals(subProtocol)) {
                    this.executeTalkMessage(logicerMessage, socketAddress);
                }
                if (LogicerSubProtocol.COMMAND.equals(subProtocol)) {
                    this.executeCommandMessage(logicerMessage, socketAddress);
                }
            }else {
                channel.writeAndFlush(LogicerMessageBuilder.buildMessage(0, LogicerConstant.ASK_FOR_LOGIN_INFO, "logicer"));
            }
        }
    }

    /**
     * 处理子协议为logicer的消息
     * @param logicerMessage
     * @param socketAddress
     * @throws JsonProcessingException
     */
    public void executeLogicerMessage(LogicerMessage logicerMessage, SocketAddress socketAddress) throws JsonProcessingException {
        logger.info("{}: {}", socketAddress, logicerMessage.getLogicerContent().toString());
        // 处理Logicer子协议的消息，直接使用LogicerContent对象进行解析
        LogicerContent content = logicerMessage.getLogicerContent();
        if ("LOGIN".equals(content.getActionName())){
            // 解析actionWord
            String userName = this.loginSuccess(content.getActionWord(), socketAddress);
            if(StringUtils.isNoneBlank(userName)){
                LogicerMessageUtil.sendToAimSocketAddress(socketAddress, LogicerMessageBuilder.buildMessage(0, LogicerUtil.getPackagedLogicerMessage("Login success.", userName), "logicer"));
            }
        }
        // 将接收到的对象使用jackson序列化为字符串
        String logicerMsgStr = CompManager.jackson_object_mapper.writeValueAsString(logicerMessage);
        // 如果子协议是LOGICER就发送到MQ
        CompManager.logicer_mq_producer.batchSendLogicerMessageAsync(logicerMsgStr);
    }

    /**
     * 处理子协议类型为talk的消息
     * @param talkMessage
     * @param fromAddress
     */
    public void executeTalkMessage(LogicerMessage talkMessage, SocketAddress fromAddress){
        try {
            // 处理Talk子协议的消息，需要将LogicerContent转化为其子类，在其子类中获取相关属性后进行处理
            LogicerTalkWord logicerTalkWord = CompManager.jackson_object_mapper.readValue(talkMessage.getLogicerContent().getActionWord(),
                    LogicerTalkWord.class);
            // 修改from属性为发送者的名字
            logicerTalkWord.setFrom(CompManager.name_socketAddress_mapper.inverse().get(fromAddress));
            talkMessage.getLogicerContent().setActionWord(CompManager.jackson_object_mapper.writeValueAsString(logicerTalkWord));
            talkMessage.getLogicerContent().setActionTimeStamp(System.currentTimeMillis());
            String correctedMsg = CompManager.jackson_object_mapper.writeValueAsString(talkMessage);
            // 先将消息异步入库
            CompManager.logicer_mq_producer.batchSendTalkMessageAsync(correctedMsg);
            // 判断msg的类型
            if ("0".equals(logicerTalkWord.getType())){
                //如果是点对点消息 就直接尝试发送，
                SocketAddress aimAddress = CompManager.name_socketAddress_mapper.getOrDefault(logicerTalkWord.getTo(), null);
                if (Objects.nonNull(aimAddress)){
                    LogicerMessageUtil.sendToAimSocketAddress(aimAddress, talkMessage);
                }else {
                    logger.error("Failed to get address for {}", logicerTalkWord.getTo());
                }
            }else if ("1".equals(logicerTalkWord.getType())){
                // 如果是群消息 就获取群然后发送
                if (StringUtils.isNumeric(logicerTalkWord.getTo())){
                    LogicerMessageUtil.sendToAimTeam(Integer.parseInt(logicerTalkWord.getTo()), talkMessage);
                }else {
                    logger.error("Error talk word, cannot get aim group. talkWord: {}", logicerTalkWord);
                }
            }else if ("2".equals(logicerTalkWord.getType())){
                // todo 可扩展 填充其他类型消息的处理逻辑
                logger.info("Dealing with msg of {}", logicerTalkWord.getType());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        logger.info("{}: {}", fromAddress, talkMessage.getLogicerContent().toString());
    }

    /**
     * 处理子协议为heatBeat的消息
     * @param hbMessage
     * @param socketAddress
     */
    public void executeHeartBeatMessage(LogicerMessage hbMessage, SocketAddress socketAddress){
        String nickName = this.getNickNameForChannel(socketAddress);
        LogicerContent logicerContent = hbMessage.getLogicerContent();
        if ("EXIT".equals(logicerContent.getActionName())){
            Channel channel = CompManager.channelGroup.find(CompManager.socketAddress_channelId_mapper.get(socketAddress));
            logger.info("{}@{} disconnected.", nickName, socketAddress);
            // 特殊规则
            CompManager.socketAddress_disconnectNum_mapper.remove(socketAddress);
            CompManager.socketAddress_channelId_mapper.remove(socketAddress);
            channel.close();
        }else {
            logger.debug("Got message: [{}] from {}@{}.", logicerContent, nickName, socketAddress);
            if (LogicerConstant.LOGICER_STATE_ANSWER.equals(logicerContent.getActionWord())){
                logger.debug("{}@{} is still alive. Current disconnectTime: {}",
                        nickName, socketAddress, CompManager.socketAddress_disconnectNum_mapper.getOrDefault(socketAddress, null));
            }
            // 将断连次数重置为0
            CompManager.socketAddress_disconnectNum_mapper.put(socketAddress, 0);
        }
    }

    /**
     * 处理子协议为command的消息
     */
    public void executeCommandMessage(LogicerMessage logicerMessage, SocketAddress socketAddress) {
        String nickName = this.getNickNameForChannel(socketAddress);
        LogicerContent logicerContent = logicerMessage.getLogicerContent();
        // 判断指令的类型
        String actionName = logicerContent.getActionName();
        Integer operationType = Integer.parseInt(actionName);
        LogicerConstant.CommandOperationEnum enumByCode = LogicerConstant.CommandOperationEnum.getEnumByCode(operationType);
        if (enumByCode.getOperationCode() == -1){
            logger.error("Invalid command, please check operationType(actionName) in command : {}", logicerContent);
        }
        LogicerCommandWord logicerCommandWord = null;
        // 解析指令的内容
        try {
            logicerCommandWord = CompManager.jackson_object_mapper.readValue(logicerContent.getActionWord(), LogicerCommandWord.class);
            if (Objects.nonNull(logicerCommandWord)){
                // 设置指令发起者
                logicerCommandWord.setSourceMemberName(nickName);
                // 设置指令内容
                logicerCommandWord.setOperationContent(enumByCode.getOperationName());
            }
            // 将指令内容使用ObjectMapper转化成字符串后发送到MQ
            CompManager.logicer_mq_producer.batchSendCommandMessageAsync(CompManager.jackson_object_mapper.writeValueAsString(logicerCommandWord));
            // 如果指令类型是踢掉其他人 就踢掉其他人
            if (LogicerConstant.CommandOperationEnum.CLEAN_OTHER_USERS == enumByCode){
                Set<SocketAddress> values = CompManager.name_socketAddress_mapper.values();
                for (SocketAddress address : values){
                    if (!Objects.equals(socketAddress, address)){
                        this.shutdownChannelBySocketAddress(address);
                    }
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        logger.info("{}@{}: {}", nickName, socketAddress, logicerContent);
    }

    /**
     * 根据远程主机地址获取主机昵称
     *
     * @param socketAddress
     * @return
     */
    public String getNickNameForChannel(SocketAddress socketAddress) {
        return CompManager.name_socketAddress_mapper.inverse().getOrDefault(socketAddress, null);
    }

    /**
     * 客户端连接断开时需要做的动作 channel关闭后的资源清理
     */
    public void clientDisconnected(SocketAddress socketAddress){
        ChannelId channelId = CompManager.socketAddress_channelId_mapper.get(socketAddress);
        // 发送再见
        Channel channel = CompManager.channelGroup.find(channelId);
        // 清理channelGroup中的本次连接
        CompManager.channelGroup.remove(channel);
        // 清理重连次数中的该连接的记录
        CompManager.socketAddress_disconnectNum_mapper.remove(socketAddress);
        // 清理记录ChannelId的mapper中关于本次连接的记录
        CompManager.socketAddress_channelId_mapper.remove(socketAddress);
        // 清理在线用户中该连接的记录
        CompManager.name_socketAddress_mapper.inverse().remove(socketAddress);
    }

    /**
     * 客户端与服务端建立连接时的动作
     * @param channel
     */
    public void clientConnected(Channel channel){
        SocketAddress socketAddress = channel.remoteAddress();
        // channelGroup添加这次连接的对象
        CompManager.channelGroup.add(channel);
        // 记录本次连接的客户端地址与channelId的映射
        CompManager.socketAddress_channelId_mapper.put(socketAddress, channel.id());
        // 初始化本次连接的远程地址的重连次数
        CompManager.socketAddress_disconnectNum_mapper.put(socketAddress, 0);
    }

    public void clientLogin(String userName, SocketAddress socketAddress, User user){
        // 记录登录的用户
        CompManager.name_socketAddress_mapper.put(userName, socketAddress);
        CompManager.name_user_mapper.put(userName, user);
    }

    /**
     * 登录信息正确 登陆成功
     * @param actionWord
     * @param socketAddress
     * @return
     */
    private String loginSuccess(String actionWord, SocketAddress socketAddress) {
        if (!LogicerUtil.isLoginStr(actionWord)){
            return null;
        }
        String[] split = actionWord.split("@");
        if (split.length != 2){
            return null;
        }
        String userName = split[0];
        String password = split[1];
        UserService userService = (UserService)CompManager.serviceName_myService_mapper.get("userService");
        if (Objects.nonNull(CompManager.name_socketAddress_mapper.getOrDefault(userName, null))){
            logger.error("用户名相同: {},已登录", userName);
            return null;
        }
        User loginUser = userService.login(userName, password);
        if (Objects.nonNull(loginUser)){
            this.clientLogin(userName, socketAddress, loginUser);
            return userName;
        }
        return null;
    }

    /**
     * 检查登陆状态 true已登录 false未登录
     * @param socketAddress
     * @return
     */
    private boolean checkLoginStatus(SocketAddress socketAddress) {
        String nickNameForChannel = this.getNickNameForChannel(socketAddress);
        return Objects.nonNull(nickNameForChannel);
    }

    /**
     * 根据socketAddress获取Channel并进行关闭
     * @param address
     */
    private void shutdownChannelBySocketAddress(SocketAddress address){
        ChannelId channelId = CompManager.socketAddress_channelId_mapper.get(address);
        // 发送再见
        Channel channel = CompManager.channelGroup.find(channelId);
        if (Objects.nonNull(channel) && channel.isOpen()){
            channel.close();
        }
    }

}
