package com.cby.tcs.user_role.service.impl;

import com.cby.tcs.user_role.dao.UserRoleDao;
import com.cby.tcs.user_role.service.UserRoleService;
import com.cby.tcs.user_role.entity.po.UserRole;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {

  private final UserRoleDao userRoleDao;
}
