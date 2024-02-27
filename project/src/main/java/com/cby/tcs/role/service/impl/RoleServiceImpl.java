package com.cby.tcs.role.service.impl;

import com.cby.tcs.role.dao.RoleDao;
import com.cby.tcs.role.service.RoleService;
import com.cby.tcs.role.entity.po.Role;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

  private final RoleDao roleDao;
}
