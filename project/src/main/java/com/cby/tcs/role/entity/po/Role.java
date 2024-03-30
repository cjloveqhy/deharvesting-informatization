package com.cby.tcs.role.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.freedom.cloud.enums.LogicalEnum;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("role")
@EqualsAndHashCode(callSuper = true)
public class Role extends Model<Role> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 角色名称
     */
    @TableField("name")
    private String name;

    /**
     * 角色优先级
     */
    @TableField("level")
    private Integer level;

    /**
     * 备注
     */
    @TableField(value = "remark", updateStrategy = FieldStrategy.IGNORED)
    private String remark;

    /**
     * 角色标识
     */
    @TableField(value = "value")
    private String value;

    /**
     * 是否为默认角色
     */
    @TableField(value = "status")
    private LogicalEnum status;

    /**
     * 是否禁用
     */
    @TableField(value = "disabled")
    private LogicalEnum disabled;

    /**
     * 逻辑删除
     */
    @TableLogic
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
