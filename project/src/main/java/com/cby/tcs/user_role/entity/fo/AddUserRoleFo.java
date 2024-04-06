package com.cby.tcs.user_role.entity.fo;

import com.cby.tcs.user.entity.enums.SexEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AddUserRoleFo implements Serializable {

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
     * 角色id
     */
    private String roleId;

    /**
     * 额外权限，当角色权限不可满足时，可通过此属性进行添加
     */
    private List<String> attachedPermission = Collections.emptyList();

}
