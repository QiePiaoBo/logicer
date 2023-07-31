package com.dylan.blog.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class ConfettiVO implements Serializable {

    private Integer id;

    private Integer userId;

    private String title;

    private String content;

}
