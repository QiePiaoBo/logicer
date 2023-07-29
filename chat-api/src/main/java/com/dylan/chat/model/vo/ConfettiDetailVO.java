package com.dylan.chat.model.vo;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConfettiDetailVO extends ConfettiVO{

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
