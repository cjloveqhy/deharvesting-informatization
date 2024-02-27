package com.cby.tcs.permission.service.impl;

import com.cby.tcs.permission.dao.PermissionDao;
import com.cby.tcs.permission.service.PermissionService;
import com.cby.tcs.permission.entity.po.Permission;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, Permission> implements PermissionService {

  private final PermissionDao permissionDao;
}
