package com.cby.tcs.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

  private final RoleDao roleDao;


  @Override
  @Transactional(rollbackFor = Exception.class)
  public void add(AddRole entity) {
    LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Role::getName, entity.getName());
    Role role = getOne(wrapper);
    if (Objects.nonNull(role)) {
      throw new RoleException("角色【%s】已存在", role.getName());
    }
    role = new Role().setName(entity.getName()).setLevel(entity.getLevel());
    if (!StrUtil.hasBlank(entity.getRemark())) role.setRemark(entity.getRemark());
    save(role);
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
      wrapper.eq(Role::getName, rolePage.getName());
    }
    Page<Role> page = page(rolePage.getPageObj(Role.class), wrapper);
    Page<RoleVo> roleVoPage = new Page<>();
    BeanUtil.copyProperties(page, roleVoPage, "records");
    roleVoPage.setRecords(BeanUtil.copyToList(page.getRecords(), RoleVo.class));
    return roleVoPage;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateRole(UpdateRole entity) {
    Role role = getById(entity.getId());
    if (Objects.isNull(role)) {
      throw new RoleException("该权限不存在");
    }
    role = new Role()
            .setName(entity.getName())
            .setLevel(entity.getLevel())
            .setRemark(entity.getRemark());
    updateById(role);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void del(String id) {
    LambdaUpdateWrapper<Role> wrapper = new LambdaUpdateWrapper<>();
    wrapper.eq(Role::getId, id);
    remove(wrapper);
  }

  @Override
  public List<RoleVo> getByRoleIds(List<String> roleIds) {
    LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
    wrapper.in(Role::getId, roleIds);
    return BeanUtil.copyToList(list(wrapper), RoleVo.class);
  }

}
