package com.cby.tcs.permission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cby.tcs.permission.entity.fo.AddPermissionFo;
import com.cby.tcs.permission.entity.fo.UpdatePermissionFo;
import com.cby.tcs.permission.entity.po.Permission;
import com.cby.tcs.permission.entity.vo.PermissionTree;

import java.util.List;

public interface PermissionService extends IService<Permission> {


    /**
     * 获取树型菜单权限
     *
     * @return {@link List}<{@link PermissionTree}>
     */
    List<PermissionTree> getTree();

    /**
     * 获取指定服务标识的所有权限
     * @return {@link List}<{@link Permission}>
     */
    List<Permission> getList();

    /**
     * 创建子服务权限根节点
     */
    void addRootNode();

    /**
     * 获取权限系统根节点
     * @return {@link Permission}
     */
    Permission getServiceRoot();

    /**
     * 生成子服务排序编号
     * @return {@link Integer}子服务排序编号
     */
    Integer nextSubServerSortNum();

    /**
     * 添加菜单权限
     * @param entity {@link AddPermissionFo} 添加实体
     */
    void addPermission(AddPermissionFo entity);

    /**
     * 修改菜单权限
     * @param entity {@link UpdatePermissionFo} 修改实体
     */
    void updatePermission(UpdatePermissionFo entity);

    /**
     * 删除菜单权限
     */
    void delPermission(String id);

    /**
     * 获取指定权限的标识
     * @param ids 权限id编号
     * @return {@link List}<{@link String}>
     */
    List<String> getPermissions(List<String> ids);
}
