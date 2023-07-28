package com.dylan.licence.transformer;


import com.dylan.licence.entity.Access;
import com.dylan.licence.model.dto.AccessDTO;
import com.dylan.licence.model.vo.AccessVO;

import java.util.Objects;

/**
 * @author Dylan
 * @Date : 2022/5/9 - 23:30
 */
public class AccessTransformer {

    /**
     * accessDTO -> access
     * @param accessDTO
     * @return
     */
    public static Access accessDTO2Access(AccessDTO accessDTO){
        Access access = new Access();
        if (Objects.isNull(accessDTO)){
            return access;
        }
        access.setId(accessDTO.getId());
        access.setRequestType(accessDTO.getRequestType());
        access.setParentMenu(accessDTO.getParentMenu());
        access.setAccessCode(accessDTO.getAccessCode());
        access.setAccessName(accessDTO.getAccessName());
        access.setAccessType(accessDTO.getAccessType());
        access.setAccessUri(accessDTO.getAccessUri());
        access.setAccessDescription(accessDTO.getAccessDescription());
        access.setAccessStatus(accessDTO.getAccessStatus());
        return access;
    }

    /**
     * access -> accessVO
     * @param access
     * @return
     */
    public static AccessVO access2AccessVO(Access access){
        AccessVO accessVO = new AccessVO();
        if (Objects.isNull(access)){
            return accessVO;
        }
        accessVO.setId(access.getId());
        accessVO.setParentMenu(access.getParentMenu());
        accessVO.setRequestType(access.getRequestType());
        accessVO.setAccessCode(access.getAccessCode());
        accessVO.setAccessName(access.getAccessName());
        accessVO.setAccessType(access.getAccessType());
        accessVO.setAccessUri(access.getAccessUri());
        accessVO.setAccessDescription(access.getAccessDescription());
        accessVO.setAccessStatus(access.getAccessStatus());
        return accessVO;
    }


}
