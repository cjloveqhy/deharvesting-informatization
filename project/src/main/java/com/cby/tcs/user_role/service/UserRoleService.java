package com.cby.tcs.user_role.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cby.tcs.user_role.entity.fo.AddUserRoleFo;
import com.cby.tcs.user_role.entity.fo.UpdateUserRoleFo;
import com.cby.tcs.user_role.entity.fo.UserRolePage;
import com.cby.tcs.user_role.entity.po.UserRole;
import com.cby.tcs.user_role.entity.vo.UserRoleVo;

import java.util.List;

public interface UserRoleService extends IService<UserRole> {


    /**
     * 获取用户角色的所有权限
     * @param userId 用户id编号
     * @return {@link List}<{@link String}> 权限集合
     */
    List<String> getRolePermissions(String userId);

    /**
     * 获取用户所有角色中最大的角色
     * @param userId 用户id编号
     * @return {@link UserRole}
     */
    UserRole getMaxLevelRole(String userId);

    /**
     * 根据用户id和服务标识获取用户角色记录
     *
     * @param userId    用户id编号
     * @return {@link UserRole}>
     */
    List<UserRole> getUserRoleByUserId(String userId);

    /**
     * 更新用户角色
     * @param entity {@link UpdateUserRoleFo}
     */
    void updateUserRole(UpdateUserRoleFo entity);

    /**
     * 添加用户角色权限
     * @param entity {@link AddUserRoleFo}
     */
    void addUserRole(AddUserRoleFo entity);

    /**
     * 删除用户角色权限
     * @param id id编号
     */
    void delUserRole(String id);

    /**
     * 分页过滤获取用户角色列表
     * @param entity {@link UserRolePage} 分页过滤信息
     * @return {@link Page}<{@link UserRoleVo}>
     */
    Page<UserRoleVo> getUserRolePage(UserRolePage entity);

    /**
     * 获取用户的所有角色
     * @param userId 用户id编号
     * @return {@link List}<{@link String}>
     */
    List<String> getRoles(String userId);
}
