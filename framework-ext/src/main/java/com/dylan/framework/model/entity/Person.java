package com.dylan.framework.model.entity;


import java.io.Serializable;

/**
 * @author Dylan
 * @since 2020-05-24
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户权限组
     */
    private Integer userGroup;

    /**
     * 用户权限组
     */
    private Integer userType;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 用户手机
     */
    private String userPhone;


    public Person(Integer id, Integer userGroup,
                  String userName, String userPassword,
                  String userPhone) {
        this.id = id;
        this.userGroup = userGroup;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
    }

    public Person() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(Integer userGroup) {
        this.userGroup = userGroup;
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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", userGroup=" + userGroup +
                ", userType=" + userType +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }
}
