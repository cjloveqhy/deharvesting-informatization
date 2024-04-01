package com.cby.tcs.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.user_role.entity.fo.AddUserRoleFo;
import com.cby.tcs.user_role.entity.fo.UpdateUserRoleFo;
import com.cby.tcs.user_role.entity.fo.UserRolePage;
import com.cby.tcs.user_role.entity.vo.UserRoleVo;
import com.cby.tcs.user_role.service.UserRoleService;
import com.freedom.cloud.annotation.ParamsToEntity;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-role")
@RequiredArgsConstructor
public class UserRoleController {

  private final UserRoleService userRoleService;


  /**
   * 获取用户角色权限
   */
  @GetMapping("/rolePermissions")
  public ResultEntity getRolePermissions() {
    List<String> permissions = userRoleService.getRolePermissions(StpUtil.getLoginId().toString());
    return ResultEntity.success(permissions);
  }

  /**
   * 获取用户角色
   */
  @GetMapping("/roles")
  public ResultEntity getRoles() {
    List<String> roles = userRoleService.getRoles(StpUtil.getLoginId().toString());
    return ResultEntity.success(roles);
  }

  /**
   * 分页获取用户角色权限
   */
  @GetMapping("/page")
  public ResultEntity getUserRolePage(@ParamsToEntity(required = false) UserRolePage entity) {
    Page<UserRoleVo> page = userRoleService.getUserRolePage(entity);
    return ResultEntity.success(page);
  }

  /**
   * 添加用户角色权限
   */
  @PostMapping("/add")
  public ResultEntity addUserRole(@Validated @RequestBody AddUserRoleFo entity) {
    userRoleService.addUserRole(entity);
    return ResultEntity.success("添加成功");
  }

  /**
   * 修改用户角色权限
   */
  @PutMapping("/update")
  public ResultEntity updateUserRole(@Validated @RequestBody UpdateUserRoleFo entity) {
    userRoleService.updateUserRole(entity);
    return ResultEntity.success("更新成功");
  }

  /**
   * 删除用户角色权限
   */
  @DeleteMapping("/del")
  public ResultEntity delUserRole(@RequestParam String id) {
    userRoleService.delUserRole(id);
    return ResultEntity.success("删除成功");
  }
}
