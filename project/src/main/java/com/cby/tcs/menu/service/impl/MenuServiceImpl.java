package com.cby.tcs.menu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.cby.tcs.menu.entity.RouteMeta;
import com.cby.tcs.menu.entity.RouteRecord;
import com.cby.tcs.menu.service.MenuService;
import com.cby.tcs.permission.entity.po.Permission;
import com.cby.tcs.permission.service.PermissionService;
import com.cby.tcs.role_permission.entity.vo.RolePermissionVo;
import com.cby.tcs.role_permission.service.RolePermissionService;
import com.cby.tcs.user_role.entity.po.UserRole;
import com.cby.tcs.user_role.service.UserRoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedom.cloud.enums.MenuType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final UserRoleService userRoleService;

    private final RolePermissionService rolePermissionService;

    private final PermissionService permissionService;

    private final ObjectMapper objectMapper;

    @Override
    public List<RouteRecord> getMenu(String userId) {
        UserRole maxLevelRole = userRoleService.getMaxLevelRole(userId);
        RolePermissionVo rolePermission = rolePermissionService.getRolePermissionByRoleId(maxLevelRole.getRoleId());
        List<String> permissionIds = rolePermission.getPermissions();
        if (!StrUtil.hasBlank(maxLevelRole.getAttachedPermission())) {
            permissionIds.addAll(Arrays.stream(maxLevelRole.getAttachedPermission().split(",")).toList());
        }
        List<Permission> permissions = permissionService.getList();
        Permission root = getServiceRoot(permissions);
        if (Objects.isNull(root)) {
            return Collections.emptyList();
        }
        List<RouteRecord> firstNode = getFirstNode(permissions, root);
        for (RouteRecord record : firstNode) {
            children(permissions, record);
        }
        return firstNode;
    }

    public Permission getServiceRoot(List<Permission> permissions) {
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

    @SneakyThrows
    public List<RouteRecord> getFirstNode(List<Permission> permissions, Permission root) {
        List<RouteRecord> records = new ArrayList<>();
        for (int i = 0; i < permissions.size(); i++) {
            Permission permission = permissions.get(i);
            if (permission.getParentId().equals(root.getId())) {
                records.add(new RouteRecord()
                        .setId(permission.getId())
                        .setPath(permission.getPath())
                        .setName(permission.getName())
                        .setComponent(permission.getComponent())
                        .setMeta(objectMapper.readValue(permission.getMeta(), RouteMeta.class)));
                permissions.remove(permission);
                --i;
            }
        }
        records.sort(Comparator.comparing(item -> item.getMeta().getSort()));
        return records;
    }

    @SneakyThrows
    public void children(List<Permission> permissions, RouteRecord parentNode) {
        for (Permission permission : permissions) {
            if (permission.getParentId().equals(parentNode.getId())) {
                RouteRecord record = new RouteRecord();
                record.setId(permission.getId())
                        .setPath(permission.getPath())
                        .setName(permission.getName())
                        .setComponent(permission.getComponent())
                        .setMeta(objectMapper.readValue(permission.getMeta(), RouteMeta.class));
                parentNode.getChildren().add(record);
                children(permissions, record);
                if (!parentNode.getChildren().isEmpty()) {
                    parentNode.getChildren().sort(Comparator.comparing(item -> item.getMeta().getSort()));
                    parentNode.setRedirect(parentNode.getPath() + "/" + parentNode.getChildren().get(0).getPath());
                }
            }
        }
    }
}
