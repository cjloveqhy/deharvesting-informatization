package com.cby.tcs.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.exception.RoleException;
import com.cby.tcs.role.dao.RoleDao;
import com.cby.tcs.role.entity.fo.AddRole;
import com.cby.tcs.role.entity.fo.RolePage;
import com.cby.tcs.role.entity.fo.UpdateRole;
import com.cby.tcs.role.entity.po.Role;
import com.cby.tcs.role.entity.vo.RoleVo;
import com.cby.tcs.role.service.RoleService;
import com.cby.tcs.role_permission.dao.RolePermissionDao;
import com.cby.tcs.role_permission.entity.po.RolePermission;
import com.freedom.cloud.enums.LogicalEnum;
import com.freedom.cloud.options.Option;
import com.freedom.cloud.utils.page.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

  private final RoleDao roleDao;

  private final RolePermissionDao rolePermissionDao;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void add(AddRole entity) {
    LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Role::getName, entity.getName());
    Role role = getOne(wrapper);
    if (Objects.nonNull(role)) {
      throw new RoleException("角色【%s】已存在", role.getName());
    }
    role = BeanUtil.copyProperties(entity, Role.class);
    if (!StrUtil.hasBlank(entity.getRemark())) role.setRemark(entity.getRemark());
    save(role);
    RolePermission rolePermission = new RolePermission().setRoleId(role.getId());
    if (!entity.getPermissions().isEmpty())
      rolePermission.setPermissions(ArrayUtil.join(entity.getPermissions(), ","));
    rolePermissionDao.insert(rolePermission);
  }

  @Override
  public List<RoleVo> roles() {
    List<Role> list = list();
    return list.isEmpty() ? Collections.emptyList() : BeanUtil.copyToList(list, RoleVo.class);
  }

  @Override
  public Page<RoleVo> getRolePage(RolePage rolePage) {
    LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
    if (!StrUtil.hasBlank(rolePage.getName())) {
      wrapper.like(Role::getName, rolePage.getName());
    }
    if (!StrUtil.hasBlank(rolePage.getValue())) {
      wrapper.like(Role::getValue, rolePage.getValue());
    }
    if (Objects.nonNull(rolePage.getStatus())) {
      wrapper.eq(Role::getStatus, rolePage.getStatus());
    }
    if (Objects.nonNull(rolePage.getDisabled())) {
      wrapper.eq(Role::getDisabled, rolePage.getDisabled());
    }
    Page<Role> page = page(PageUtils.getPage(rolePage), wrapper);
    Page<RoleVo> roleVoPage = new Page<>();
    BeanUtil.copyProperties(page, roleVoPage, "records");
    roleVoPage.setRecords(BeanUtil.copyToList(page.getRecords(), RoleVo.class));
    List<String> roleIds = roleVoPage.getRecords().stream().map(RoleVo::getId).toList();
    if (roleIds.isEmpty()) return roleVoPage;
    LambdaQueryWrapper<RolePermission> rpWrapper = new LambdaQueryWrapper<>();
    rpWrapper.in(RolePermission::getRoleId, roleIds);
    List<RolePermission> list = rolePermissionDao.selectList(rpWrapper);
    Map<String, List<String>> map = new HashMap<>();
    for (RolePermission rolePermission : list) {
      List<String> permissions = new ArrayList<>();
      if (!StrUtil.hasBlank(rolePermission.getPermissions())) {
        permissions.addAll(Arrays.stream(rolePermission.getPermissions().split(",")).toList());
      }
      map.put(rolePermission.getRoleId(), permissions);
    }
    for (RoleVo vo : roleVoPage.getRecords()) {
      vo.setPermissions(map.getOrDefault(vo.getId(), Collections.emptyList()));
    }
    return roleVoPage;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateRole(UpdateRole entity) {
    Role role = getById(entity.getId());
    if (Objects.isNull(role)) {
      throw new RoleException("该权限不存在");
    }
    BeanUtil.copyProperties(entity, role);
    if (!entity.getPermissions().isEmpty()) {
      LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
      wrapper.eq(RolePermission::getRoleId, role.getId());
      RolePermission rolePermission = rolePermissionDao.selectOne(wrapper);
      String permissions = ArrayUtil.join(entity.getPermissions().toArray(String[]::new), ",");
      if (Objects.nonNull(rolePermission)) {
        rolePermission.setPermissions(permissions);
        rolePermissionDao.updateById(rolePermission);
      } else {
        rolePermission = new RolePermission().setPermissions(permissions);
        rolePermissionDao.insert(rolePermission);
      }
    }
    updateById(role);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void del(String id) {
    LambdaUpdateWrapper<Role> wrapper = new LambdaUpdateWrapper<>();
    wrapper.eq(Role::getId, id);
    remove(wrapper);
    rolePermissionDao.delete(new LambdaUpdateWrapper<RolePermission>().eq(RolePermission::getRoleId, id));
  }

  @Override
  public List<RoleVo> getByRoleIds(List<String> roleIds) {
    LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
    wrapper.in(Role::getId, roleIds);
    return BeanUtil.copyToList(list(wrapper), RoleVo.class);
  }

  @Override
  public List<Option<String>> getOptions() {
    LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Role::getStatus, LogicalEnum.NO);
    List<Role> roles = list(wrapper);
    return roles.stream().map(item -> new Option<>(item.getName(), item.getId())).toList();
  }

}
