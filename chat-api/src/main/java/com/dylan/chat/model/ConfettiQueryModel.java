package com.dylan.chat.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @Classname MsgRecordEntity
 * @Description MsgRecordEntity
 * @Date 6/20/2023 5:14 PM
 */
@Data
public class ConfettiQueryModel {

    private Integer id;

    private Integer userId;

    /**
     * 标题
     */
    private String title;

    /**
     * QueryModel要求三个参数必须有一个值不为空
     * @return
     */
    public boolean isValid() {
        if (Objects.isNull(getId()) && Objects.isNull(getUserId()) && StringUtils.isBlank(title)){
            return false;
        }
        return true;
    }
}
