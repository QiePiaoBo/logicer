package com.dylan.blog.converter;


import com.dylan.blog.entity.ConfettiEntity;
import com.dylan.blog.model.ConfettiInsertModel;
import com.dylan.blog.vo.ConfettiListVO;
import com.dylan.blog.vo.ConfettiVO;

public class ConfettiConverter {

    /**
     * 从数据库实体类中读取属性并赋值给ConfettiVO
     * @param entity
     * @return
     */
    public static ConfettiVO getConfettiVO(ConfettiEntity entity){
        ConfettiListVO confettiVO = new ConfettiListVO();
        confettiVO.setContent(entity.getContent());
        confettiVO.setId(entity.getId());
        confettiVO.setTitle(entity.getTitle());
        confettiVO.setUserId(entity.getUserId());
        confettiVO.setLockFlag(entity.getLockFlag());
        confettiVO.setCreatedAt(entity.getCreatedAt());
        return confettiVO;
    }

    /**
     * 从数据库实体类中读取属性并赋值给ConfettiVO
     * @param insertModel
     * @return
     */
    public static ConfettiVO getConfettiVO(ConfettiInsertModel insertModel){
        ConfettiVO confettiVO = new ConfettiVO();
        confettiVO.setId(insertModel.getId());
        confettiVO.setTitle(insertModel.getTitle());
        confettiVO.setUserId(insertModel.getUserId());
        confettiVO.setContent(insertModel.getContent());
        confettiVO.setLockFlag(insertModel.getLockFlag());
        return confettiVO;
    }


}
