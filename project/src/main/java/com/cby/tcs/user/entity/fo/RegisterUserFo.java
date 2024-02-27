package com.cby.tcs.user.entity.fo;

import jakarta.validation.constraints.NotBlank;
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
public class RegisterUserFo implements Serializable {

    @NotBlank(message = "请输入账户名")
    private String account;

    @NotBlank(message = "请输入密码")
    private String password;

    @NotBlank(message = "请输入确认密码")
    private String rePassword;

}
