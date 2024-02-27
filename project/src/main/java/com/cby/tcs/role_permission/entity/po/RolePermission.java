package com.cby.tcs.role_permission.entity.po;

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
@TableName("role_permission")
@EqualsAndHashCode(callSuper = true)
public class RolePermission extends Model<RolePermission> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 角色id
     */
    @TableField("role_id")
    private String roleId;

    /**
     * 权限集合
     */
    @TableField("permissions")
    private String permissions;

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
