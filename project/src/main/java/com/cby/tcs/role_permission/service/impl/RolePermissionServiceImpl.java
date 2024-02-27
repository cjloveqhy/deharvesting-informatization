package com.cby.tcs.role_permission.service.impl;

import com.cby.tcs.role_permission.entity.po.RolePermission;
import com.cby.tcs.role_permission.dao.RolePermissionDao;
import com.cby.tcs.role_permission.service.RolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionDao, RolePermission> implements RolePermissionService {

  private final RolePermissionDao rolePermissionDao;
}
