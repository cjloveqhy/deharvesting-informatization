package com.cby.tcs.user.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserAutoInfo implements Serializable {

    private String id;

    /**
     * 令牌
     */
    private String token;

    /**
     * 账户名
     */
    private String account;

    /**
     * 用户信息
     */
    private UserInfo info;

}
