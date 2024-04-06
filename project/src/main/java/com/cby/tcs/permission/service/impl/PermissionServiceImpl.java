package com.cby.tcs.permission.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.exception.PermissionException;
import com.cby.tcs.menu.entity.RouteMeta;
import com.cby.tcs.permission.dao.PermissionDao;
import com.cby.tcs.permission.entity.fo.AddPermissionFo;
import com.cby.tcs.permission.entity.fo.UpdatePermissionFo;
import com.cby.tcs.permission.entity.po.Permission;
import com.cby.tcs.permission.entity.vo.PermissionTree;
import com.cby.tcs.permission.service.PermissionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedom.cloud.enums.MenuType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, Permission> implements PermissionService {

  private final PermissionDao permissionDao;

  private final ObjectMapper objectMapper;

  @Override
  public List<PermissionTree> getTree() {
    List<Permission> permissions = getList();
    Permission root = getAndDelServiceNode(permissions);
    if (Objects.isNull(root)) return Collections.emptyList();
    List<PermissionTree> firstNode = getFirstNode(permissions, root);
    for (PermissionTree record : firstNode) {
      children(permissions, record);
    }
    PermissionTree rootNode = BeanUtil.copyProperties(root, PermissionTree.class, "meta");
    rootNode.setChildren(firstNode);
    rootNode.setMeta(stringToMeta(root.getMeta()));
    List<PermissionTree> list = new ArrayList<>();
    list.add(rootNode);
    return list;
  }

  @Override
  public Permission getAndDelServiceNode(List<Permission> permissions) {
    Permission permission = new Permission();
    for (Permission item : permissions) {
      if (item.getType().equals(MenuType.Service)) {
        BeanUtil.copyProperties(item, permission);
        permissions.remove(item);
        break;
      }
    }
    return permission;
  }

  public List<PermissionTree> getFirstNode(List<Permission> permissions, Permission root) {
    List<PermissionTree> records = new ArrayList<>();
    for (int i = 0; i < permissions.size(); i++) {
      Permission permission = permissions.get(i);
      if (permission.getParentId().equals(root.getId())) {
        records.add(BeanUtil.copyProperties(permission, PermissionTree.class, "meta")
                .setMeta(stringToMeta(permission.getMeta())));
        permissions.remove(permission);
        --i;
      }
    }
    records.sort(Comparator.comparing(item -> item.getMeta().getSort()));
    return records;
  }

  /**
   * 组装树型权限
   * @param permissions 所有可用权限
   * @param tree 根节点
   * @return {@link PermissionTree} 生成完整的权限树
   */
  public void children(List<Permission> permissions, PermissionTree tree) {
    for (Permission permission : permissions) {
      if (permission.getParentId().equals(tree.getId())) {
        PermissionTree permissionTree = BeanUtil.copyProperties(permission, PermissionTree.class, "meta")
                .setMeta(stringToMeta(permission.getMeta()));
        tree.getChildren().add(permissionTree);
        children(permissions, permissionTree);
        if (!tree.getChildren().isEmpty()) {
          List<PermissionTree> notSortRouters = tree.getChildren().stream().filter(item -> Objects.isNull(item.getMeta().getSort())).toList();
          List<PermissionTree> sortRouters = new LinkedList<>(tree.getChildren()
                  .stream()
                  .filter(item -> Objects.nonNull(item.getMeta().getSort()))
                  .toList());
          if (!sortRouters.isEmpty()) {
            sortRouters.sort(Comparator.comparing(item -> item.getMeta().getSort()));
          }
          if (!notSortRouters.isEmpty()) {
            sortRouters.addAll(notSortRouters);
          }
          tree.setChildren(sortRouters);
        }
      }
    }
  }

  @Override
  public List<Permission> getList() {
    return list();
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void addRootNode() {
    RouteMeta meta = new RouteMeta();
    meta.setSort(nextSubServerSortNum());
    Permission permission = new Permission()
            .setParentId(getServiceRoot().getId())
            .setName("服务权限")
            .setType(MenuType.Service)
            .setMeta(metaToString(meta));
    save(permission);
  }

  @Override
  public Permission getServiceRoot() {
    LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Permission::getType, MenuType.Service);
    return getOne(wrapper);
  }

  @Override
  public Integer nextSubServerSortNum() {
    Permission root = getServiceRoot();
    LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Permission::getParentId, root.getId()).ne(Permission::getType, MenuType.Root);
    return Math.toIntExact(count(wrapper) + 1L);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void addPermission(AddPermissionFo entity) {
    LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Permission::getParentId, entity.getParentId())
            .eq(Permission::getName, entity.getName());
    if (Objects.nonNull(getOne(wrapper))) throw new PermissionException("权限名称【%s】已存在，不可重复创建");
    Permission permission = BeanUtil.copyProperties(entity, Permission.class, "meta");
    Permission oldSortPermission = getOneBySort(entity.getParentId(), entity.getMeta().getSort());
    if (Objects.nonNull(oldSortPermission)) {
      wrapper.clear();
      wrapper.eq(Permission::getParentId, entity.getParentId());
      Integer newPermissionSort = Math.toIntExact(count(wrapper) + 1);
      oldSortPermission.setMeta(metaToString(stringToMeta(oldSortPermission.getMeta()).setSort(newPermissionSort)));
      updateById(oldSortPermission);
    }
    if (Objects.nonNull(entity.getMeta())) {
      permission.setMeta(metaToString(entity.getMeta()));
    }
    save(permission);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updatePermission(UpdatePermissionFo entity) {
    Permission oldPermission = getById(entity.getId());
    if (Objects.isNull(oldPermission)) throw new PermissionException("该菜单记录不存在");
    Permission permission = BeanUtil.copyProperties(entity, Permission.class, "meta");
    RouteMeta oldMeta = stringToMeta(oldPermission.getMeta());
    if (!oldMeta.getSort().equals(entity.getMeta().getSort())) {
      Permission oldSortPermission = getOneBySort(entity.getParentId(), entity.getMeta().getSort());
      oldSortPermission.setMeta(metaToString(stringToMeta(oldSortPermission.getMeta()).setSort(oldMeta.getSort())));
      updateById(oldSortPermission);
    }
    if (Objects.nonNull(entity.getMeta())) {
      permission.setMeta(metaToString(entity.getMeta()));
    }
    updateById(permission);
  }

  public Permission getOneBySort(String parentId, Integer sort) {
    LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Permission::getParentId, parentId).like(Permission::getMeta, String.format("\"sort\":%s", sort));
    return getOne(wrapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delPermission(String id) {
    LambdaUpdateWrapper<Permission> wrapper = new LambdaUpdateWrapper<>();
    wrapper.eq(Permission::getId, id);
    remove(wrapper);
  }

  @Override
  public List<String> getPermissions(List<String> ids) {
    ArrayList<String> permissions = new ArrayList<>();
    LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
    wrapper.in(Permission::getId, ids);
    List<Permission> list = list(wrapper);
    for (Permission permission : list) {
      RouteMeta meta = stringToMeta(permission.getMeta());
      if (Objects.nonNull(meta.getPermissions()) && !meta.getPermissions().isEmpty()) {
        permissions.addAll(meta.getPermissions());
      }
    }
    return permissions.stream().distinct().toList();
  }

  public RouteMeta stringToMeta(String meta) {
    try {
      return objectMapper.readValue(meta, RouteMeta.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public String metaToString(RouteMeta meta) {
    try {
      return objectMapper.writeValueAsString(meta);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
