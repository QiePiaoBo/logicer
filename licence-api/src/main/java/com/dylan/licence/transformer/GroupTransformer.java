package com.dylan.licence.transformer;


import com.dylan.licence.entity.Group;
import com.dylan.licence.model.dto.GroupDTO;
import com.dylan.licence.model.vo.GroupVO;

import java.util.Objects;

/**
 * @Classname GroupTransformer
 * @Description GroupTransformer
 * @Date 5/10/2022 3:54 PM
 */
public class GroupTransformer {

    /**
     * groupDTO -> group
     * @param groupDTO
     * @return
     */
    public static Group groupDTO2Group(GroupDTO groupDTO){
        Group group = new Group();
        if (Objects.isNull(groupDTO)){
            return group;
        }
        group.setId(groupDTO.getId());
        group.setGroupCode(groupDTO.getGroupCode());
        group.setGroupName(groupDTO.getGroupName());
        group.setGroupRole(groupDTO.getGroupRole());
        group.setGroupStatus(groupDTO.getGroupStatus());
        return group;
    }

    /**
     * group -> groupVO
     * @param group
     * @return
     */
    public static GroupVO group2GroupVO(Group group){
        GroupVO groupVO = new GroupVO();
        if (Objects.isNull(group)){
            return groupVO;
        }
        groupVO.setId(group.getId());
        groupVO.setGroupCode(group.getGroupCode());
        groupVO.setGroupName(group.getGroupName());
        groupVO.setGroupRole(group.getGroupRole());
        groupVO.setGroupStatus(group.getGroupStatus());
        return groupVO;
    }


}