package com.cby.tcs.role_permission.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.exception.RolePermissionException;
import com.cby.tcs.role.entity.po.Role;
import com.cby.tcs.role.entity.vo.RoleVo;
import com.cby.tcs.role.service.RoleService;
import com.cby.tcs.role_permission.dao.RolePermissionDao;
import com.cby.tcs.role_permission.entity.fo.AddRolePermissionFo;
import com.cby.tcs.role_permission.entity.fo.RolePermissionPage;
import com.cby.tcs.role_permission.entity.fo.UpdateRolePermissionFo;
import com.cby.tcs.role_permission.entity.po.RolePermission;
import com.cby.tcs.role_permission.entity.vo.RolePermissionVo;
import com.cby.tcs.role_permission.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionDao, RolePermission> implements RolePermissionService {

  private final RolePermissionDao rolePermissionDao;

  private final RoleService roleService;

  @Override
  public List<RolePermissionVo> getRolePermission() {
    ArrayList<RolePermissionVo> rolePermissionVos = new ArrayList<>();
    List<RolePermission> rolePermissions = getListByServerTag();
    if (rolePermissions.isEmpty()) return rolePermissionVos;
    List<String> roleIds = rolePermissions.stream().map(RolePermission::getRoleId).toList();
    Map<String, Role> roleMap = roleService.listByIds(roleIds)
            .stream()
            .collect(Collectors.toMap(Role::getId, Function.identity()));
    for (RolePermission rolePermission : rolePermissions) {
      rolePermissionVos.add(new RolePermissionVo()
              .setId(rolePermission.getId())
              .setPermissions(StrUtil.hasBlank(rolePermission.getPermissions()) ? Collections.emptyList() : Arrays.stream(rolePermission.getPermissions().split(",")).toList())
              .setRole(BeanUtil.copyProperties(roleMap.get(rolePermission.getRoleId()), RoleVo.class)));
    }
    return rolePermissionVos;
  }

  @Override
  public RolePermissionVo getRolePermissionByRoleId(String roleId) {
    LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(RolePermission::getRoleId, roleId);
    RolePermission rolePermission = getOne(wrapper);
    return new RolePermissionVo()
            .setId(rolePermission.getId())
            .setPermissions(StrUtil.hasBlank(rolePermission.getPermissions()) ? Collections.emptyList() : Arrays.stream(rolePermission.getPermissions().split(",")).toList())
            .setRole(BeanUtil.copyProperties(roleService.getById(roleId), RoleVo.class));
  }

  @Override
  public List<RolePermission> getListByServerTag() {
    return list();
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void addRolePermission(AddRolePermissionFo entity) {
    RolePermission rolePermission = new RolePermission();
    LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(RolePermission::getRoleId, entity.getRoleId());
    if (Objects.nonNull(getOne(wrapper))) throw new RolePermissionException("该角色权限已存在，不可重复配置");
    rolePermission.setRoleId(entity.getRoleId())
            .setPermissions(entity.getPermissions().isEmpty() ? null : ArrayUtil.join(entity.getPermissions().toArray(String[]::new), ","));
    save(rolePermission);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateRolePermission(UpdateRolePermissionFo entity) {
    RolePermission rolePermission = new RolePermission();
    if (Objects.isNull(getById(entity.getId()))) throw new RolePermissionException("该角色权限不存在，请重新设置");
    rolePermission.setId(entity.getId())
            .setRoleId(entity.getRoleId())
            .setPermissions(Objects.isNull(entity.getPermissions()) || entity.getPermissions().isEmpty() ? null : ArrayUtil.join(entity.getPermissions().toArray(String[]::new), ","));
    updateById(rolePermission);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delRolePermission(String id) {
    LambdaUpdateWrapper<RolePermission> wrapper = new LambdaUpdateWrapper<>();
    wrapper.eq(RolePermission::getId, id);
    remove(wrapper);
  }

  @Override
  public Page<RolePermissionVo> getRolePermissionPage(RolePermissionPage entity) {
    LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
    if (Objects.nonNull(entity.getRoleIds()) && !entity.getRoleIds().isEmpty()) {
      wrapper.in(RolePermission::getRoleId, entity.getRoleIds());
    }
    if (Objects.nonNull(entity.getPermissions()) && !entity.getPermissions().isEmpty()) {
      wrapper.in(RolePermission::getRoleId, entity.getRoleIds());
    }
    Page<RolePermission> page = page(entity.getPageObj(RolePermission.class), wrapper);
    Page<RolePermissionVo> rolePermissionVoPage = new Page<>();
    BeanUtil.copyProperties(page, rolePermissionVoPage, "records");
    List<String> roleIds = page.getRecords().stream().map(RolePermission::getRoleId).toList();
    Map<String, RoleVo> roleVoMap = roleService.getByRoleIds(roleIds)
            .stream()
            .collect(Collectors.toMap(RoleVo::getId, Function.identity()));
    for (RolePermission record : page.getRecords()) {
      List<String> permissions = StrUtil.hasBlank(record.getPermissions()) ? Collections.emptyList() : Arrays.stream(record.getPermissions().split(",")).toList();
      rolePermissionVoPage.getRecords().add(new RolePermissionVo(record.getId(), roleVoMap.get(record.getRoleId()), permissions));
    }
    return rolePermissionVoPage;
  }
}
