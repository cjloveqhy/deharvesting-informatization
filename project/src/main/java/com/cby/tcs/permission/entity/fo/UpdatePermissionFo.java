package com.cby.tcs.permission.entity.fo;

import com.cby.tcs.menu.entity.RouteMeta;
import com.freedom.cloud.enums.MenuType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class UpdatePermissionFo implements Serializable {

    @NotBlank(message = "id编号不可为空")
    private String id;

    /**
     * 所属上级
     */
    @NotBlank(message = "请选择上级菜单")
    private String parentId;

    /**
     * 名称
     */
    @NotBlank(message = "请输入名称")
    private String name;

    /**
     * 类型（R：权限系统根节点，M：菜单，B：按钮，C：内容）
     */
    @NotNull(message = "请选择类型")
    private MenuType type;

    /**
     * 访问路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 元数据
     */
    private RouteMeta meta;

}
