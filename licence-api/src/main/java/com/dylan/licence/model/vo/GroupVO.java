package com.dylan.licence.model.vo;

import java.io.Serializable;

/**
 * @Classname GroupVO
 * @Description GroupVO
 * @Date 5/10/2022 3:53 PM
 */
public class GroupVO  implements Serializable {

    private Integer id;

    private String groupCode;

    private String groupName;

    private Integer groupRole;

    private Integer groupStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupRole() {
        return groupRole;
    }

    public void setGroupRole(Integer groupRole) {
        this.groupRole = groupRole;
    }

    public Integer getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(Integer groupStatus) {
        this.groupStatus = groupStatus;
    }

    @Override
    public String toString() {
        return "GroupVO{" +
                "id=" + id +
                ", groupCode='" + groupCode + '\'' +
                ", groupName='" + groupName + '\'' +
                ", groupRole=" + groupRole +
                ", groupStatus=" + groupStatus +
                '}';
    }
}
