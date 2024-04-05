package com.cby.tcs.user_role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.exception.UserException;
import com.cby.tcs.exception.UserRoleException;
import com.cby.tcs.permission.service.PermissionService;
import com.cby.tcs.role.entity.po.Role;
import com.cby.tcs.role.entity.vo.RoleVo;
import com.cby.tcs.role.service.RoleService;
import com.cby.tcs.role_permission.entity.vo.RolePermissionVo;
import com.cby.tcs.role_permission.service.RolePermissionService;
import com.cby.tcs.user.entity.po.User;
import com.cby.tcs.user.entity.vo.UserInfo;
import com.cby.tcs.user.entity.vo.ValidAccountVo;
import com.cby.tcs.user.service.UserService;
import com.cby.tcs.user_role.dao.UserRoleDao;
import com.cby.tcs.user_role.entity.dto.FilterPageUserDTO;
import com.cby.tcs.user_role.entity.fo.AddUserRoleFo;
import com.cby.tcs.user_role.entity.fo.UpdateUserRoleFo;
import com.cby.tcs.user_role.entity.fo.UserRolePage;
import com.cby.tcs.user_role.entity.po.UserRole;
import com.cby.tcs.user_role.entity.vo.UserRoleVo;
import com.cby.tcs.user_role.service.UserRoleService;
import com.freedom.cloud.config.AppConfigProperties;
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

  private final AppConfigProperties appConfigProperties;

  @Override
  public List<String> getRolePermissions(String userId) {
    List<String> permissionIds = getRolePermissionIds(userId);
    return permissionService.getPermissions(permissionIds);
  }

  @Override
  public List<String> getRolePermissionIds(String userId) {
    List<String> defaultPermissions = roleService.getDefaultPermissions();
    UserRole maxLevelRole = getMaxLevelRole(userId);
    Set<String> permissionIds = new HashSet<>();
    if (!defaultPermissions.isEmpty())
      permissionIds.addAll(defaultPermissions);
    if (Objects.nonNull(maxLevelRole)) {
      RolePermissionVo rolePermissionVo = rolePermissionService.getRolePermissionByRoleId(maxLevelRole.getRoleId());
      if (!rolePermissionVo.getPermissions().isEmpty()) {
        permissionIds.addAll(rolePermissionVo.getPermissions());
      }
      if (!StrUtil.hasBlank(maxLevelRole.getAttachedPermission())) {
        permissionIds.addAll(Arrays.stream(maxLevelRole.getAttachedPermission().split(",")).toList());
      }
    }
    return permissionIds.stream().toList();
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
    User user = userService.getById(entity.getId());
    if (Objects.isNull(user)) throw new UserRoleException("该用户不存在");
    if (!user.getAccount().equals(entity.getAccount())) {
      ValidAccountVo validAccount = userService.validAccount(entity.getAccount());
      if (!validAccount.isUsable()) throw new UserException(validAccount.getMessage());
    }
    user = BeanUtil.copyProperties(entity, User.class);
    if (StrUtil.isNotBlank(entity.getRoleId())) {
      LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
      wrapper.eq(UserRole::getUserId, entity.getId())
              .eq(UserRole::getRoleId, entity.getRoleId());
      UserRole userRole = getOne(wrapper);
      if (Objects.isNull(userRole)) {
        userRole = new UserRole().setRoleId(entity.getRoleId()).setUserId(user.getId());
      }
      if (Objects.nonNull(entity.getAttachedPermission())) {
        String attachedPermission = ArrayUtil.join(entity.getAttachedPermission().toArray(String[]::new), ",");
        userRole.setAttachedPermission(attachedPermission);
      }
      saveOrUpdate(userRole);
    }
    userService.updateById(user);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void addUserRole(AddUserRoleFo entity) {
    LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(User::getAccount, entity.getAccount());
    User user = userService.getOne(wrapper);
    if (Objects.nonNull(user)) throw new UserException("该账户名已被注册，不可重复注册");
    if (StrUtil.isNotBlank(entity.getPhone())) {
      wrapper.clear();
      wrapper.eq(User::getPhone, entity.getPhone());
      user = userService.getOne(wrapper);
      if (Objects.nonNull(user)) throw new UserException("该手机号已被注册");
    }
    if (StrUtil.isNotBlank(entity.getEmail())) {
      wrapper.clear();
      wrapper.eq(User::getEmail, entity.getEmail());
      user = userService.getOne(wrapper);
      if (Objects.nonNull(user)) throw new UserException("该邮箱已被注册");
    }
    user = BeanUtil.copyProperties(entity, User.class);
    user.setPassword(DigestUtil.md5Hex(appConfigProperties.getUser().getPassword()));
    userService.save(user);
    if (StrUtil.isBlank(entity.getRoleId()) && entity.getAttachedPermission().isEmpty()) return;
    UserRole userRole = new UserRole().setUserId(user.getId());
    if (StrUtil.isNotBlank(entity.getRoleId())) userRole.setRoleId(entity.getRoleId());
    if (Objects.nonNull(entity.getAttachedPermission()) && !entity.getAttachedPermission().isEmpty()) {
      String attachedPermission = ArrayUtil.join(entity.getAttachedPermission().toArray(String[]::new), ",");
      userRole.setAttachedPermission(attachedPermission);
    }
    save(userRole);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delUserRole(String userId) {
    userService.removeById(userId);
    LambdaUpdateWrapper<UserRole> wrapper = new LambdaUpdateWrapper<>();
    wrapper.eq(UserRole::getUserId, userId);
    remove(wrapper);
  }

  @Override
  public Page<UserRoleVo> getUserRolePage(UserRolePage entity) {
    Page<FilterPageUserDTO> filterPage = userService.filterPage(entity);
    Page<UserRoleVo> userRoleVoPage = new Page<>();
    userRoleVoPage.setRecords(new ArrayList<>());
    BeanUtil.copyProperties(filterPage, userRoleVoPage, "records");
    List<String> roleIds = filterPage.getRecords()
            .stream()
            .map(FilterPageUserDTO::getRoleId)
            .filter(StrUtil::isNotBlank)
            .distinct()
            .toList();
    Map<String, RoleVo> roleVoMap = new HashMap<>();
    if (!roleIds.isEmpty()) {
      roleVoMap.putAll(roleService.getByRoleIds(roleIds)
              .stream()
              .collect(Collectors.toMap(RoleVo::getId, Function.identity())));
    }
    for (FilterPageUserDTO record : filterPage.getRecords()) {
      List<String> permissions = StrUtil.hasBlank(record.getAttachedPermission()) ? Collections.emptyList() : Arrays.stream(record.getAttachedPermission().split(",")).toList();
      userRoleVoPage.getRecords().add(new UserRoleVo(record.getId(), BeanUtil.copyProperties(record, UserInfo.class), roleVoMap.get(record.getRoleId()), permissions));
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
