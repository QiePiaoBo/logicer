package com.dylan.chat.service;

import com.dylan.chat.dal.entity.SessionEntity;
import com.dylan.chat.dal.mapper.SessionMapper;
import com.dylan.chat.dal.mapper.UserMapper;
import com.dylan.chat.model.SessionInsertModel;
import com.dylan.chat.model.SessionQueryModel;
import com.dylan.chat.model.UserNameIdModel;
import com.dylan.chat.model.converter.SessionConverter;
import com.dylan.chat.model.vo.LgcTalkSessionVO;
import com.dylan.framework.utils.Safes;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Classname MsgRecordService
 * @Description MsgRecordService
 * @Date 6/28/2023 3:27 PM
 */
@Service
public class SessionService {

    private static final MyLogger logger = MyLoggerFactory.getLogger(SessionService.class);

    @Resource
    private SessionMapper sessionMapper;

    @Resource
    private UserMapper userMapper;


    /**
     * 按条件查询session
     *
     * @param queryModel
     * @return
     */
    public List<LgcTalkSessionVO> querySessions(SessionQueryModel queryModel) {
        if (!queryModel.isValid()) {
            return new ArrayList<>();
        }
        queryModel.confirmId();
        List<SessionEntity> entities = sessionMapper.querySessions(queryModel);
        return Safes.of(entities).stream().map(SessionConverter::getVoForEntity).collect(Collectors.toList());
    }


    /**
     * 创建session
     *
     * @return
     */
    public boolean createSession(SessionInsertModel insertModel) {
        if (!insertModel.isOk()) {
            return false;
        }
        insertModel.confirmId();
        Integer integer = sessionMapper.insertSession(insertModel);
        return integer > 0;
    }


    /**
     * 根据用户名查询或新建session
     *
     * @param userName
     * @param teamId
     * @return
     */
    public Integer getOrCreateSessionForTeam(String userName, Integer teamId) {
        // 用户名都为空且群名称不为空
        if (StringUtils.isBlank(userName) || Objects.isNull(teamId)){
            return null;
        }
        SessionQueryModel queryModel = SessionQueryModel.builder().talkTeamId(teamId).build();
        List<SessionEntity> entities = sessionMapper.querySessions(queryModel);
        if (entities.size() == 1){
            return entities.get(0).getSessionId();
        }
        return null;
    }

    /**
     * 为用户查询或创建sessionId 并将入参的值修改为查询到的Id
     * @param senderId
     * @param receiptId
     * @return
     */
    public Integer getOrCreateSessionForUser(Integer senderId, Integer receiptId) {
        if (Objects.isNull(senderId) || Objects.isNull(receiptId)){
            return null;
        }
        // 构造查询对象
        SessionQueryModel sessionQueryModel = SessionQueryModel
                .builder()
                .senderId(senderId)
                .recipientId(receiptId)
                .build();
        sessionQueryModel.confirmId();
        // 构造插入对象
        SessionInsertModel sessionInsertModel = SessionInsertModel
                .builder()
                .senderId(senderId)
                .recipientId(receiptId)
                .build();
        sessionInsertModel.confirmId();
        // 首次查询
        List<LgcTalkSessionVO> vos = this.querySessions(sessionQueryModel);
        if (vos.size() > 0) {
            LgcTalkSessionVO sessionVO = vos.get(0);
            logger.info("<getOrCreateSession> Got entityFromTo: {}", sessionVO);
            if (Objects.nonNull(sessionVO)) {
                return sessionVO.getSessionId();
            }
        } else {
            // 创建session
            if (this.createSession(sessionInsertModel)) {
                logger.info("<getOrCreateSession> Session inserted, {}", sessionInsertModel);
            } else {
                logger.error("<getOrCreateSession> Error param of sessionInsertModel: {}", sessionInsertModel);
                return null;
            }
            // 如果回补失败，就查询并取第一个值的id进行返回
            if (Objects.nonNull(sessionInsertModel.getSessionId())) {
                return sessionInsertModel.getSessionId();
            } else {
                // 插入成功后再次查询
                List<LgcTalkSessionVO> entityList = this.querySessions(sessionQueryModel);
                return entityList.get(0).getSessionId();
            }
        }
        logger.error("<getOrCreateSession> Error getting sessionId for {} and {}", senderId, receiptId);
        return null;
    }

    /**
     * 根据用户名获取用户名Id映射
     *
     * @param userNames
     * @return
     */
    public Map<String, Integer> getUserNameIdMap(List<String> userNames) {
        List<UserNameIdModel> userNameIds = userMapper.getUserNameId(userNames);
        Map<String, Integer> userNameIdMap = Safes.of(userNameIds).stream().filter(m -> m.getId() > 0).collect(Collectors.toMap(UserNameIdModel::getUserName, UserNameIdModel::getId, (v1, v2) -> v2));
        if (userNameIdMap.size() != userNames.size()) {
            logger.error("<getOrCreateSession> Error getting username id map : {}", userNameIds);
            return null;
        }
        return userNameIdMap;
    }
}
