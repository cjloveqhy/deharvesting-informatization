package com.cby.tcs.role_permission.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cby.tcs.role_permission.entity.fo.AddRolePermissionFo;
import com.cby.tcs.role_permission.entity.fo.RolePermissionPage;
import com.cby.tcs.role_permission.entity.fo.UpdateRolePermissionFo;
import com.cby.tcs.role_permission.entity.po.RolePermission;
import com.cby.tcs.role_permission.entity.vo.RolePermissionVo;

import java.util.List;

public interface RolePermissionService extends IService<RolePermission> {

    /**
     * 获取角色权限列表
     * @return {@link List}<{@link RolePermissionVo}> 结果集
     */
    List<RolePermissionVo> getRolePermission();

    /**
     * 根据角色id获取角色所拥有的权限
     * @param roleId 角色id编号
     * @return {@link RolePermissionVo}>
     */
    RolePermissionVo getRolePermissionByRoleId(String roleId);

    /**
     * 根据服务表示获取角色权限绑定关系
     * @return {@link List}<{@link RolePermission}>
     */
    List<RolePermission> getListByServerTag();

    /**
     * 添加角色权限绑定记录
     * @param entity {@link AddRolePermissionFo}
     */
    void addRolePermission(AddRolePermissionFo entity);

    /**
     * 更新角色权限绑定记录
     * @param entity {@link UpdateRolePermissionFo}
     */
    void updateRolePermission(UpdateRolePermissionFo entity);

    /**
     * 删除角色权限绑定记录
     * @param id id编号
     */
    void delRolePermission(String id);

    /**
     * 分页获取角色权限
     * @param entity {@link RolePermissionPage} 分页过滤信息
     * @return {@link Page}<{@link RolePermissionVo}>
     */
    Page<RolePermissionVo> getRolePermissionPage(RolePermissionPage entity);
}
