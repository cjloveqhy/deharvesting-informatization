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
public class ValidAccountVo implements Serializable {

    /**
     * 提示账户名是否可用的消息
     */
    private String message;

    /**
     * 提示账户名是否可用
     */
    private boolean usable;
}
