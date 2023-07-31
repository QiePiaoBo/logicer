package com.dylan.blog.vo;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ConfettiListVO extends ConfettiVO implements Serializable {

    private LocalDateTime createdAt;

}
