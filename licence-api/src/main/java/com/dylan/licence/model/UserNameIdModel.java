package com.dylan.licence.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname UserNameIdModel
 * @Description UserNameIdModel
 * @Date 6/30/2023 11:07 AM
 */
@Data
public class UserNameIdModel implements Serializable {

    private String userName;

    private Integer id;

}
