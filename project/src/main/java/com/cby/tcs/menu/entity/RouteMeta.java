package com.cby.tcs.menu.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RouteMeta implements Serializable {

    /**
     * 菜单名称 一般必填
     */
    private String title;

    /**
     * 禁用菜单
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean disabled;

    /**
     * 菜单图标
      */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String icon;

    /**
     * 缓存该路由
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean keepAlive;

    /**
     * 隐藏菜单
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean hidden;

    /**
     * 排序越小越排前
     */
    private Integer sort;

    /**
     * 取消自动计算根路由模式
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean alwaysShow;

    /**
     * 当路由设置了该属性，则会高亮相对应的侧边栏。
     * 这在某些场景非常有用，比如：一个列表页路由为：/list/basic-list
     * 点击进入详情页，这时候路由为/list/basic-info/1，但你想在侧边栏高亮列表的路由，就可以进行如下设置
     * 注意是配置高亮路由 `name`，不是path
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String activeMenu;

    /**
     * 是否跟路由 顶部混合菜单，必须传 true，否则左侧会显示异常（场景就是，分割菜单之后，当一级菜单没有子菜单）
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isRoot;

    /**
     * 内联外部地址
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String frameSrc;

    /**
     * 菜单包含权限集合，满足其中一个就会显示
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> permissions;

    /**
     * 是否固定 设置为 true 之后 多页签不可删除
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean affix;
}
