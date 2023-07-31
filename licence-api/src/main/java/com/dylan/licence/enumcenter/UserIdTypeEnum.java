package com.dylan.licence.enumcenter;

import java.io.Serializable;

/**
 * @Classname UserIdTypeEnum
 * @Description UserIdTypeEnum
 * @Date 5/7/2022 5:00 PM
 */
public enum UserIdTypeEnum implements Serializable {

    /**
     * 中华人民共和国居民身份证
     */
    CN_ID      (0,"中华人民共和国居民身份证"),
    /**
     * 军官证
     */
    ARMY_ID    (1,"军官证"),
    /**
     * 户口本
     */
    HOUSEHOLD_ID(2,"户口本");

    private final Integer typeId;
    private final String typeName;

    UserIdTypeEnum(Integer typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }
    public Integer getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }
}
