package com.cby.tcs.user_role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.exception.UserRoleException;
import com.cby.tcs.permission.service.PermissionService;
import com.cby.tcs.role.entity.po.Role;
import com.cby.tcs.role.entity.vo.RoleVo;
import com.cby.tcs.role.service.RoleService;
import com.cby.tcs.role_permission.entity.vo.RolePermissionVo;
import com.cby.tcs.role_permission.service.RolePermissionService;
import com.cby.tcs.user.entity.vo.UserInfo;
import com.cby.tcs.user.service.UserService;
import com.cby.tcs.user_role.dao.UserRoleDao;
import com.cby.tcs.user_role.entity.fo.AddUserRoleFo;
import com.cby.tcs.user_role.entity.fo.UpdateUserRoleFo;
import com.cby.tcs.user_role.entity.fo.UserRolePage;
import com.cby.tcs.user_role.entity.po.UserRole;
import com.cby.tcs.user_role.entity.vo.UserRoleVo;
import com.cby.tcs.user_role.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {

  private final UserRoleDao userRoleDao;

  private final RoleService roleService;

  private final RolePermissionService rolePermissionService;

  private final UserService userService;

  private final PermissionService permissionService;

  @Override
  public List<String> getRolePermissions(String userId) {
    UserRole maxLevelRole = getMaxLevelRole(userId);
    if (Objects.isNull(maxLevelRole)) return Collections.emptyList();
    RolePermissionVo rolePermissionVo = rolePermissionService.getRolePermissionByRoleId(maxLevelRole.getRoleId());
    ArrayList<String> permissionIds = new ArrayList<>();
    if (!rolePermissionVo.getPermissions().isEmpty()) {
      permissionIds.addAll(rolePermissionVo.getPermissions());
    }
    if (!StrUtil.hasBlank(maxLevelRole.getAttachedPermission())) {
      permissionIds.addAll(Arrays.stream(maxLevelRole.getAttachedPermission().split(",")).toList());
    }
    return permissionService.getPermissions(permissionIds);
  }

  @Override
  public UserRole getMaxLevelRole(String userId) {
    List<UserRole> userRoles = getUserRoleByUserId(userId);
    UserRole userRole = null;
    if (userRoles.size() == 1) {
      userRole = userRoles.get(0);
    } else if (userRoles.size() > 1) {
      // 若有多个，则找出角色优先级大的进行使用
      List<String> roleIds = userRoles.stream().map(UserRole::getRoleId).toList();
      List<Role> roles = roleService.listByIds(roleIds);
      roles.sort(Comparator.comparing(Role::getLevel, Comparator.naturalOrder()));
      for (UserRole role : userRoles) {
        if (roles.get(0).getId().equals(role.getRoleId())) {
          userRole = role;
          break;
        }
      }
    }
    return userRole;
  }

  @Override
  public List<UserRole> getUserRoleByUserId(String userId) {
    LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(UserRole::getUserId, userId);
    return list(wrapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateUserRole(UpdateUserRoleFo entity) {
    UserRole userRole = getById(entity.getId());
    if (Objects.isNull(userRole)) throw new UserRoleException("该用户角色条目不存在，请重写设置");
    userRole.setUserId(entity.getUserId())
            .setRoleId(entity.getRoleId())
            .setAttachedPermission(Objects.isNull(entity.getAttachedPermission()) || entity.getAttachedPermission().isEmpty() ? null : ArrayUtil.join(entity.getAttachedPermission().toArray(String[]::new), ","));
    updateById(userRole);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void addUserRole(AddUserRoleFo entity) {
    LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(UserRole::getUserId, entity.getUserId())
            .eq(UserRole::getRoleId, entity.getRoleId());
    if (Objects.nonNull(getOne(wrapper))) {
      Role role = roleService.getById(entity.getRoleId());
      throw new UserRoleException("角色【%s】已被赋予，不可重复", role.getName());
    }
    UserRole userRole = new UserRole();
    userRole.setUserId(entity.getUserId())
            .setRoleId(entity.getRoleId())
            .setAttachedPermission(Objects.isNull(entity.getAttachedPermission()) || entity.getAttachedPermission().isEmpty() ? null : ArrayUtil.join(entity.getAttachedPermission().toArray(String[]::new), ","));
    save(userRole);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delUserRole(String id) {
    LambdaUpdateWrapper<UserRole> wrapper = new LambdaUpdateWrapper<>();
    wrapper.eq(UserRole::getId, id);
    remove(wrapper);
  }

  @Override
  public Page<UserRoleVo> getUserRolePage(UserRolePage entity) {
    LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
    if (Objects.nonNull(entity.getUserIds()) && !entity.getUserIds().isEmpty()) {
      wrapper.in(UserRole::getUserId, entity.getUserIds());
    }
    if (Objects.nonNull(entity.getRoleIds()) && !entity.getRoleIds().isEmpty()) {
      wrapper.in(UserRole::getRoleId, entity.getRoleIds());
    }
    if (Objects.nonNull(entity.getAttachedPermissions()) && !entity.getAttachedPermissions().isEmpty()) {
      wrapper.in(UserRole::getAttachedPermission, entity.getAttachedPermissions());
    }
    Page<UserRole> page = page(entity.getPageObj(UserRole.class), wrapper);
    Page<UserRoleVo> userRoleVoPage = new Page<>();
    BeanUtil.copyProperties(page, userRoleVoPage, "records");
    List<String> roleIds = page.getRecords().stream().map(UserRole::getRoleId).toList();
    Map<String, RoleVo> roleVoMap = roleService.getByRoleIds(roleIds)
            .stream()
            .collect(Collectors.toMap(RoleVo::getId, Function.identity()));
    List<String> userIds = page.getRecords().stream().map(UserRole::getUserId).toList();
    Map<String, UserInfo> userInfoMap = userService.getUserInfoList(userIds)
            .stream()
            .collect(Collectors.toMap(UserInfo::getId, Function.identity()));
    for (UserRole record : page.getRecords()) {
      List<String> permissions = StrUtil.hasBlank(record.getAttachedPermission()) ? Collections.emptyList() : Arrays.stream(record.getAttachedPermission().split(",")).toList();
      userRoleVoPage.getRecords().add(new UserRoleVo(record.getId(), userInfoMap.get(record.getUserId()), roleVoMap.get(record.getRoleId()), permissions));
    }
    return userRoleVoPage;
  }

  @Override
  public List<String> getRoles(String userId) {
    LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(UserRole::getUserId, userId);
    List<UserRole> list = list(wrapper);
    if (list.isEmpty()) return Collections.emptyList();
    List<String> roleIds = list.stream().map(UserRole::getRoleId).toList();
    List<Role> roles = roleService.listByIds(roleIds);
    return roles.stream().map(Role::getName).distinct().toList();
  }

}
