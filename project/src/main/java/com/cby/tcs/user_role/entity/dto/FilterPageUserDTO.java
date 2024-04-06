package com.cby.tcs.user_role.entity.dto;

import com.cby.tcs.user.entity.enums.SexEnum;
import com.freedom.cloud.enums.LogicalEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FilterPageUserDTO {

    private String id;

    /**
     * 账户名
     */
    private String account;

    /**
     * 用户姓名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户头像
     */
    private String photo;

    /**
     * 用户性别
     */
    private SexEnum sex;

    /**
     * 是否禁用
     */
    private LogicalEnum status;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 额外权限，当角色权限不可满足时，可通过此属性进行添加
     */
    private String attachedPermission;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
