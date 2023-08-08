package com.dylan.blog.vo;


import java.io.Serializable;
import java.time.LocalDateTime;

public class ConfettiListVO extends ConfettiVO implements Serializable {

    private LocalDateTime createdAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
