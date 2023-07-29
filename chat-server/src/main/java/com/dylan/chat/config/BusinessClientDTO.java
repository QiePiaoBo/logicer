package com.dylan.chat.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname BusinessClientDTO
 * @Description BusinessClientDTO
 * @Date 7/6/2023 5:42 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessClientDTO {

    private String sessionId;

    private String userName;

    private String password;

    /**
     * 0 点对点聊天 1 群聊
     */
    private String msgAreaType;

}
