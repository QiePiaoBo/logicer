package com.dylan.licence.model.dto;

import java.io.Serializable;

/**
 * @Classname RoleDTO
 * @Description RoleDTO
 * @Date 5/9/2022 11:06 AM
 */
public class RoleDTO implements Serializable {

    private Integer id;

    private String roleCode;

    private String roleName;

    private Integer pid;

    private String roleDescription;

    private Integer roleStatus;

    private Integer roleSort;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Integer getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }

    public Integer getRoleSort() {
        return roleSort;
    }

    public void setRoleSort(Integer roleSort) {
        this.roleSort = roleSort;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", roleCode='" + roleCode + '\'' +
                ", roleName='" + roleName + '\'' +
                ", pid=" + pid +
                ", roleDescription='" + roleDescription + '\'' +
                ", roleStatus=" + roleStatus +
                ", roleSort=" + roleSort +
                '}';
    }
}
