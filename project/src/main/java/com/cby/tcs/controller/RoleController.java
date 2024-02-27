package com.cby.tcs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.role.entity.fo.AddRole;
import com.cby.tcs.role.entity.fo.RolePage;
import com.cby.tcs.role.entity.fo.UpdateRole;
import com.cby.tcs.role.entity.vo.RoleVo;
import com.cby.tcs.role.service.RoleService;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

  private final RoleService roleService;

  /**
   * 添加角色
   */
  @PostMapping("/add")
  public ResultEntity add(@Validated @RequestBody AddRole entity) {
    roleService.add(entity);
    return ResultEntity.success("添加成功");
  }

  /**
   * 获取角色列表
   */
  @GetMapping("/list")
  public ResultEntity roles() {
    List<RoleVo> roles = roleService.roles();
    return ResultEntity.success(roles);
  }

  /**
   * 分页获取角色列表
   */
  @PostMapping("/page")
  public ResultEntity getRolePage(@RequestBody RolePage rolePage) {
    Page<RoleVo> page = roleService.getRolePage(rolePage);
    return ResultEntity.success(page);
  }

  /**
   * 更新角色信息
   */
  @PutMapping("/update")
  public ResultEntity updateRole(@Validated @RequestBody UpdateRole entity) {
    roleService.updateRole(entity);
    return ResultEntity.success("更新成功");
  }

  /**
   * 删除角色
   */
  @DeleteMapping("/del")
  public ResultEntity delRole(@RequestParam String id) {
    roleService.del(id);
    return ResultEntity.success("删除成功");
  }
}
