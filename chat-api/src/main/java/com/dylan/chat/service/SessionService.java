package com.dylan.chat.service;

import com.dylan.chat.model.SessionInsertModel;
import com.dylan.chat.model.SessionQueryModel;
import com.dylan.chat.model.vo.LgcTalkSessionVO;

import java.util.List;
import java.util.Map;

public interface SessionService {
    /**
     * 按条件查询session
     *
     * @param queryModel
     * @return
     */
    List<LgcTalkSessionVO> querySessions(SessionQueryModel queryModel);

    /**
     * 创建session
     *
     * @return
     */
    boolean createSession(SessionInsertModel insertModel);

    /**
     * 根据用户名查询或新建session
     *
     * @param userName
     * @param teamId
     * @return
     */
    Integer getOrCreateSessionForTeam(String userName, Integer teamId);

    /**
     * 为用户查询或创建sessionId 并将入参的值修改为查询到的Id
     *
     * @param senderId
     * @param receiptId
     * @return
     */
    Integer getOrCreateSessionForUser(Integer senderId, Integer receiptId);

    /**
     * 根据用户名获取用户名Id映射
     *
     * @param userNames
     * @return
     */
    Map<String, Integer> getUserNameIdMap(List<String> userNames);
}
