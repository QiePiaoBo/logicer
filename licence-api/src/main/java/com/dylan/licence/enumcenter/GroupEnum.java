package com.dylan.licence.enumcenter;

import java.io.Serializable;

/**
 * @author Dylan
 */
public enum  GroupEnum implements Serializable {

    /**
     * 超级管理员
     */
    SUPER_USER      (0,"超管"),
    /**
     * 管理员
     */
    MANAGER_USER    (1,"管理员用户"),
    /**
     * 普通用户
     */
    USER_GROUP  (2,"普通用户");

    private Integer groupId;
    private String groupName;

    GroupEnum(Integer groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }
    public Integer getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }
}
