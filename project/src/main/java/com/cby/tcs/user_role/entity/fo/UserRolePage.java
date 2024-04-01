package com.cby.tcs.user_role.entity.fo;

import com.cby.tcs.user.entity.enums.SexEnum;
import com.freedom.cloud.utils.page.PageQuery;
import lombok.Data;

@Data
public class UserRolePage extends PageQuery<UserRolePage> {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户性别
     */
    private SexEnum sex;

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
}
