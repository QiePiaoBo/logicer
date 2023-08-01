package com.dylan.comm.util;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.dylan.comm.comp.CompManager;
import com.dylan.comm.dao.entity.User;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.dylan.logicer.base.logicer.LogicerConstant;
import com.dylan.logicer.base.logicer.LogicerMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Objects;

/**
 * 消息处理类
 */
public class LogicerMessageUtil {

    private static final MyLogger logger = MyLoggerFactory.getLogger(LogicerMessageUtil.class);

    /**
     * 给固定的人发消息
     */
    public static void sendToAimUser(String userName, Object completedMessage) {
        if (userName.equals(LogicerConstant.MESSAGE_AT_ALL)){
            // @all
            Collection<ChannelId> values = CompManager.socketAddress_channelId_mapper.values();
            for (ChannelId channelId : values){
                Channel channel = CompManager.channelGroup.find(channelId);
                if (Objects.nonNull(channel) && channel.isOpen()){
                    channel.writeAndFlush(completedMessage);
                }
            }
        }else {
            // userName
            SocketAddress address = CompManager.name_socketAddress_mapper.getOrDefault(userName, null);
            if (Objects.isNull(address)){
                return;
            }
            ChannelId channelIdForAddress = CompManager.socketAddress_channelId_mapper.getOrDefault(address, null);
            if (Objects.isNull(channelIdForAddress)){
                return;
            }
            Channel aimChannelForUserName = CompManager.channelGroup.find(channelIdForAddress);
            aimChannelForUserName.writeAndFlush(completedMessage);
        }
    }

    /**
     * 向SocketAddress发送消息
     * @param socketAddress
     * @param completeMessage
     */
    public static void sendToAimSocketAddress(SocketAddress socketAddress, LogicerMessage completeMessage){
        // 接收用户不在线的情况 todo 接收用户不在线，消息保存并标记未读？？？
        ChannelId channelId = CompManager.socketAddress_channelId_mapper.getOrDefault(socketAddress, null);
        if (Objects.isNull(channelId)){
            logger.error("Error, cannot find channelId for address: {}", socketAddress);
        }
        Channel channel = CompManager.channelGroup.find(channelId);
        if (Objects.isNull(channel) || !channel.isOpen()){
            logger.error("Error, cannot find channel or channel is not open. channelId: {}", channelId);
        }
        channel.writeAndFlush(completeMessage);
    }

    /**
     * 给固定的用户组发消息
     * @param groupCode 用户类型
     * @param completeMessage 消息体
     */
    public static void sendToAimUserType(Integer groupCode, LogicerMessage completeMessage){
        Integer aimGroup = LogicerConstant.GroupEnum.getByGroupCode(groupCode).getGroupCode();
        if (aimGroup.equals(LogicerConstant.GroupEnum.ERROR_USER.getGroupCode())){
            logger.error("Error, cannot execute error group: {}", aimGroup);
        }
        Collection<User> users = CompManager.name_user_mapper.values();
        if (CollectionUtils.isNotEmpty(users)){
            for (User user : users){
                if (Objects.equals(user.getUserGroup(), aimGroup)){
                    sendToAimUser(user.getUserName(), completeMessage);
                }
            }
        }

    }

    /**
     * 向目标群发送消息
     * @param teamId 群id
     * @param completeMessage 消息
     */
    public static void sendToAimTeam(Integer teamId, LogicerMessage completeMessage) {
        // 获取所有登录用户 并向其中teamId为目的群id的用户发送消息
        Collection<User> users = CompManager.name_user_mapper.values();
        if (CollectionUtils.isNotEmpty(users)){
            for (User user : users){
                if (Objects.equals(user.getTeamId(), teamId)){
                    sendToAimUser(user.getUserName(), completeMessage);
                }
            }
        }
    }


}
