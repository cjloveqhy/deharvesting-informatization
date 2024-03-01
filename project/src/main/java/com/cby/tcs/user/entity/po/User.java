package com.cby.tcs.user.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.cby.tcs.user.entity.enums.SexEnum;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("user")
@EqualsAndHashCode(callSuper = true)
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 账户名
     */
    @TableField("account")
    private String account;

    /**
     * 用户姓名
     */
    @TableField(value = "username", updateStrategy = FieldStrategy.IGNORED)
    private String username;

    /**
     * 用户密码
     */
    @TableField("password")
    private String password;

    /**
     * 手机号
     */
    @TableField(value = "phone", updateStrategy = FieldStrategy.IGNORED)
    private String phone;

    /**
     * 邮箱
     */
    @TableField(value = "email", updateStrategy = FieldStrategy.IGNORED)
    private String email;

    /**
     * 用户头像
     */
    @TableField("photo")
    private String photo;

    /**
     * 用户性别
     */
    @TableField("sex")
    private SexEnum sex;

    /**
     *逻辑删除
     */
    @TableLogic("deleted")
    private Integer deleted;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;



}
