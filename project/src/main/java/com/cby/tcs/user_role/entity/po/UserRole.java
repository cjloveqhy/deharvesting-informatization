package com.cby.tcs.user_role.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("user_role")
@EqualsAndHashCode(callSuper = true)
public class UserRole extends Model<UserRole> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 角色id
     */
    @TableField("role_id")
    private String roleId;

    /**
     * 额外权限，当角色权限不可满足时，可通过此属性进行添加
     */
    @TableField("attached_permission")
    private String attachedPermission;

    /**
     * 逻辑删除
     */
    @TableField("deleted")
    private Boolean deleted;

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
