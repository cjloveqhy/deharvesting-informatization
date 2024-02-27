package com.cby.tcs.role.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cby.tcs.role.entity.fo.AddRole;
import com.cby.tcs.role.entity.fo.RolePage;
import com.cby.tcs.role.entity.fo.UpdateRole;
import com.cby.tcs.role.entity.po.Role;
import com.cby.tcs.role.entity.vo.RoleVo;

import java.util.List;

public interface RoleService extends IService<Role> {


    /**
     * 添加角色
     * @param entity {@link AddRole}
     */
    void add(AddRole entity);

    /**
     * 根据服务标识获取权限列表
     * @return {@link List}<{@link Role}>
     */
    List<RoleVo> roles();

    /**
     * 根据过滤条件获取分页数据
     * @param rolePage {@link RolePage} 过滤条件
     * @return {@link Page}<{@link RoleVo}>
     */
    Page<RoleVo> getRolePage(RolePage rolePage);

    /**
     * 更新角色信息
     * @param entity {@link UpdateRole}
     */
    void updateRole(UpdateRole entity);

    /**
     * 删除指定服务标识的角色
     * @param id 角色条目id
     */
    void del(String id);

    /**
     * 根据多个角色id获取角色信息
     * @param roleIds 角色id集合
     * @return {@link List}<{@link RoleVo}>
     */
    List<RoleVo> getByRoleIds(List<String> roleIds);

}
