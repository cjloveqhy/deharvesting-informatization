package com.cby.tcs.permission.entity.vo;

import com.freedom.cloud.enums.LogicalEnum;
import com.freedom.cloud.enums.MenuType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PermissionTree implements Serializable {

    private String id;

    /**
     * 所属上级
     */
    private String parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型（M：菜单，B：按钮，C：内容）
     */
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
    private Integer sortNum;

    /**
     * 子菜单
     */
    private List<PermissionTree> children = new ArrayList<>();

}
