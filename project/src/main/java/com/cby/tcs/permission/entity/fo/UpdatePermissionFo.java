package com.cby.tcs.permission.entity.fo;

import com.freedom.cloud.enums.LogicalEnum;
import com.freedom.cloud.enums.MenuType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

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
     * 权限值
     */
    private String permissionValue;

    /**
     * 访问路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 图标
     */
    private String icon;

    /**
     * 状态（N：禁止，Y：正常）
     */
    private LogicalEnum status;

    /**
     * 元数据
     */
    private Map<String, Object> meta;

    /**
     * 排列顺序
     */
    @NotNull(message = "请设置排列顺序")
    @Min(value = 1L, message = "最小排序编号为1，不可小于1")
    private Integer sortNum;
}
