package com.cby.tcs.permission.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.freedom.cloud.enums.MenuType;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("permission")
@EqualsAndHashCode(callSuper = true)
public class Permission extends Model<Permission> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 所属上级
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 类型（R：权限系统根节点，M：菜单，B：按钮，C：内容）
     */
    @TableField("type")
    private MenuType type;

    /**
     * 访问路径
     */
    @TableField(value = "path", updateStrategy = FieldStrategy.IGNORED)
    private String path;

    /**
     * 组件路径
     */
    @TableField(value = "component", updateStrategy = FieldStrategy.IGNORED)
    private String component;

    /**
     * 元数据
     */
    @TableField(value = "meta", updateStrategy = FieldStrategy.IGNORED)
    private String meta;

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
