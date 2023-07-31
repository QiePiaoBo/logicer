package com.dylan.licence.model.dto;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Dylan
 * @since 2020-05-24
 */
public class UserDTO implements Serializable {

    public UserDTO(Integer id, String userName, String userPassword, String userPhone, Integer userGroup, Integer userType) {
        this.id = id;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.userGroup = userGroup;
        this.userType = userType;
    }

    public UserDTO() {
    }

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户名
     */
    private String userPassword;

    /**
     * 用户手机
     */
    private String userPhone;

    private Integer userGroup;

    private Integer userType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(Integer userGroup) {
        this.userGroup = userGroup;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"userName\":").append(userName == null ? "" : "\"")
                .append(userName).append(userName == null ? "" : "\"");
        sb.append(",\"userPassword\":").append(userPassword == null ? "" : "\"")
                .append(userPassword).append(userPassword == null ? "" : "\"");
        sb.append(",\"userPhone\":").append(userPhone == null ? "" : "\"")
                .append(userPhone).append(userPhone == null ? "" : "\"");
        sb.append(",\"userGroup\":")
                .append(userGroup);
        sb.append(",\"userType\":")
                .append(userType);
        sb.append('}');
        return sb.toString();
    }
}
