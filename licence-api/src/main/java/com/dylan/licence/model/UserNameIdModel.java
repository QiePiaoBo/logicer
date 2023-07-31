package com.dylan.licence.model;

import java.io.Serializable;

/**
 * @Classname UserNameIdModel
 * @Description UserNameIdModel
 * @Date 6/30/2023 11:07 AM
 */
public class UserNameIdModel implements Serializable {

    private String userName;

    private Integer id;

    public UserNameIdModel() {
    }

    public UserNameIdModel(String userName, Integer id) {
        this.userName = userName;
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
