package com.cby.tcs.user.entity.vo;

import com.cby.tcs.user.entity.enums.SexEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserInfo implements Serializable {

    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户头像
     */
    private String photo;

    /**
     * 用户性别
     */
    private SexEnum sex;

    /**
     * 账户名
     */
    @NotBlank(message = "账户名不可设置为空")
    private String account;

    /**
     *手机号
     */
    private String phone;

    /**
     *电子邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    private LocalDateTime createTime;

}
