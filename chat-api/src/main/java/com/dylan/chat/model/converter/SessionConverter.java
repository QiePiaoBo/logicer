package com.dylan.chat.model.converter;

import com.dylan.chat.entity.SessionEntity;
import com.dylan.chat.model.vo.LgcTalkSessionVO;
import org.springframework.beans.BeanUtils;

/**
 * @Classname MsgRecordConverter
 * @Description MsgRecordConverter
 * @Date 6/28/2023 3:33 PM
 */
public class SessionConverter {

    /**
     * entity获取VO
     * @param entity
     * @return
     */
    public static LgcTalkSessionVO getVoForEntity(SessionEntity entity){
        LgcTalkSessionVO msgRecordVO = new LgcTalkSessionVO();
        BeanUtils.copyProperties(entity, msgRecordVO);
        return msgRecordVO;
    }



}
