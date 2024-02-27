package com.cby.tcs.permission.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
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
import com.fasterxml.jackson.core.type.TypeReference;
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
    ArrayList<PermissionTree> list = new ArrayList<>();
    PermissionTree tree = new PermissionTree();
    assembleTree(permissions, tree);
    list.add(tree);
    return list;
  }

  /**
   * 组装树型权限
   * @param permissions 所有可用权限
   * @param tree 根节点
   * @return {@link PermissionTree} 生成完整的权限树
   */
  public void assembleTree(List<Permission> permissions, PermissionTree tree) {
    // 设置根节点
    if (Objects.isNull(tree.getId())) {
      List<Permission> rootPermission = permissions
              .stream()
              .filter(item -> item.getType().equals(MenuType.Root))
              .toList();
      if (rootPermission.isEmpty()) throw new PermissionException("未找到根权限，请进行添加");
      if (rootPermission.size() > 1) throw new PermissionException("检测到该权限系统拥有两个根权限，请删除其中一个");
      BeanUtil.copyProperties(rootPermission.get(0), tree, "meta");
      if (!StrUtil.hasBlank(rootPermission.get(0).getMeta())) {
        // meta转成对象
        tree.setMeta(metaToMap(rootPermission.get(0).getMeta()));
      }
      permissions.remove(rootPermission.get(0));
    }
    // 通过递归方式设置子节点
    for (int i = 0; i < permissions.size(); i++) {
      if (permissions.isEmpty()) return;
      Permission permission = permissions.get(i);
      if (tree.getId().equals(permission.getParentId())) {
        PermissionTree childNode = BeanUtil.copyProperties(permission, PermissionTree.class, "meta");
        if (!StrUtil.hasBlank(permission.getMeta())) {
          // meta转成对象
          childNode.setMeta(metaToMap(permission.getMeta()));
        }
        permissions.remove(i);
        tree.getChildren().add(childNode);
        if (childNode.getType().equals(MenuType.Menu)) {
          assembleTree(permissions, childNode);
        }
        i--;
      }
    }
    // 正序排序
    tree.getChildren().sort(Comparator.comparing(PermissionTree::getSortNum));
  }

  public Map<String, Object> metaToMap(String meta) {
    try {
      return objectMapper.readValue(meta, new TypeReference<>() {});
    } catch (JsonProcessingException e) {
      throw new RuntimeException("转换失败，字符串设置异常，请检查");
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
    Permission permission = new Permission();
    LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(Permission::getParentId, entity.getParentId())
            .eq(Permission::getName, entity.getName());
    if (Objects.nonNull(getOne(wrapper))) throw new PermissionException("权限名称【%s】已存在，不可重复创建");
    BeanUtil.copyProperties(entity, permission, "meta");
    if (Objects.nonNull(entity.getMeta())) {
      permission.setMeta(metaToString(entity.getMeta()));
    }
    save(permission);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updatePermission(UpdatePermissionFo entity) {
    Permission permission = new Permission();
    if (Objects.isNull(getById(entity.getId()))) throw new PermissionException("该权限记录不存在");
    BeanUtil.copyProperties(entity, permission, "meta");
    if (Objects.nonNull(entity.getMeta())) {
      permission.setMeta(metaToString(entity.getMeta()));
    }
    updateById(permission);
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
      try {
        RouteMeta meta = objectMapper.readValue(permission.getMeta(), RouteMeta.class);
        if (!meta.getPermissions().isEmpty()) {
          permissions.addAll(meta.getPermissions());
        }
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }
    return permissions.stream().distinct().toList();
  }

  public String metaToString(RouteMeta meta) {
    return metaToString(BeanUtil.beanToMap(meta));
  }

  public String metaToString(Map<String, Object> meta) {
    try {
      return objectMapper.writeValueAsString(meta);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
