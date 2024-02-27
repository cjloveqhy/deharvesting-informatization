package com.cby.tcs.controller;

import com.cby.tcs.permission.entity.fo.AddPermissionFo;
import com.cby.tcs.permission.entity.fo.UpdatePermissionFo;
import com.cby.tcs.permission.entity.vo.PermissionTree;
import com.cby.tcs.permission.service.PermissionService;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {

  private final PermissionService permissionService;

  /**
   * 创建根节点
   */
  @PostMapping("/addRootNode")
  public ResultEntity addRootNode() {
    permissionService.addRootNode();
    return ResultEntity.success("创建成功");
  }

  /**
   * 获取权限树
   */
  @GetMapping("/tree")
  public ResultEntity getTree() {
    List<PermissionTree> tree = permissionService.getTree();
    return ResultEntity.success(tree);
  }

  /**
   * 添加菜单权限
   */
  @PostMapping("/add")
  public ResultEntity addPermission(@Validated @RequestBody AddPermissionFo entity) {
    permissionService.addPermission(entity);
    return ResultEntity.success("添加成功");
  }

  /**
   * 修改菜单权限
   */
  @PutMapping("/update")
  public ResultEntity updatePermission(@Validated @RequestBody UpdatePermissionFo entity) {
    permissionService.updatePermission(entity);
    return ResultEntity.success("修改成功");
  }

  /**
   * 删除菜单权限
   */
  @DeleteMapping("/del")
  public ResultEntity delPermission(@RequestParam String id) {
    permissionService.delPermission(id);
    return ResultEntity.success("删除成功");
  }
}
