package com.cby.tcs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.role_permission.entity.fo.AddRolePermissionFo;
import com.cby.tcs.role_permission.entity.fo.RolePermissionPage;
import com.cby.tcs.role_permission.entity.fo.UpdateRolePermissionFo;
import com.cby.tcs.role_permission.entity.vo.RolePermissionVo;
import com.cby.tcs.role_permission.service.RolePermissionService;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role-permission")
@RequiredArgsConstructor
public class RolePermissionController {

  private final RolePermissionService rolePermissionService;

  /**
   * 获取角色权限列表
   */
  @GetMapping("/list")
  public ResultEntity getList() {
    List<RolePermissionVo> vos = rolePermissionService.getRolePermission();
    return ResultEntity.success(vos);
  }

  /**
   * 分页获取角色权限列表
   */
  @PostMapping("/page")
  public ResultEntity getRolePermissionPage(@RequestBody RolePermissionPage entity) {
    Page<RolePermissionVo> page = rolePermissionService.getRolePermissionPage(entity);
    return ResultEntity.success(page);
  }

  /**
   * 添加角色权限绑定记录
   */
  @PostMapping("/add")
  public ResultEntity add(@Validated @RequestBody AddRolePermissionFo entity) {
    rolePermissionService.addRolePermission(entity);
    return ResultEntity.success("添加成功");
  }

  /**
   * 更新角色绑定记录
   */
  @PutMapping("/update")
  public ResultEntity update(@Validated @RequestBody UpdateRolePermissionFo entity) {
    rolePermissionService.updateRolePermission(entity);
    return ResultEntity.success("更新成功");
  }

  /**
   * 删除角色绑定记录
   */
  @DeleteMapping("/del")
  public ResultEntity del(@RequestParam String id) {
    rolePermissionService.delRolePermission(id);
    return ResultEntity.success("删除成功");
  }

}
