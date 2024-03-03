package com.cby.tcs.menu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cby.tcs.menu.entity.RouteMeta;
import com.cby.tcs.menu.entity.RouteRecord;
import com.cby.tcs.menu.service.MenuService;
import com.cby.tcs.permission.entity.po.Permission;
import com.cby.tcs.permission.service.PermissionService;
import com.cby.tcs.role_permission.entity.vo.RolePermissionVo;
import com.cby.tcs.role_permission.service.RolePermissionService;
import com.cby.tcs.user_role.entity.po.UserRole;
import com.cby.tcs.user_role.service.UserRoleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedom.cloud.enums.MenuType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final UserRoleService userRoleService;

    private final RolePermissionService rolePermissionService;

    private final PermissionService permissionService;

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public List<RouteRecord> getMenu(String userId) {
        UserRole maxLevelRole = userRoleService.getMaxLevelRole(userId);
        List<String> permissionIds = new ArrayList<>();
        if (Objects.nonNull(maxLevelRole)) {
            RolePermissionVo rolePermission = rolePermissionService.getRolePermissionByRoleId(maxLevelRole.getRoleId());
            permissionIds.addAll(rolePermission.getPermissions());
            if (!StrUtil.hasBlank(maxLevelRole.getAttachedPermission())) {
                permissionIds.addAll(Arrays.stream(maxLevelRole.getAttachedPermission().split(",")).toList());
            }
        }
        List<Permission> permissions = permissionService.getList();
        Permission root = getServiceRoot(permissions);
        if (Objects.isNull(root)) {
            return Collections.emptyList();
        }
        for (int i = 0; i < permissions.size(); i++) {
            Permission permission = permissions.get(i);
            RouteMeta routeMeta = objectMapper.readValue(permission.getMeta(), RouteMeta.class);
            List<String> list = routeMeta.getPermissions();
            if (Objects.nonNull(list) && !list.isEmpty()) {
                boolean match = list.stream().anyMatch(permissionIds::contains);
                if (!match) {
                    permissions.remove(permission);
                    i--;
                }
            }
        }
        List<RouteRecord> firstNode = getFirstNode(permissions, root);
        for (RouteRecord record : firstNode) {
            children(permissions, record);
        }
        return firstNode;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(List<RouteRecord> router) {
        if (Objects.isNull(router) || router.isEmpty()) return;
        List<Permission> permissions = new ArrayList<>();
        Permission serviceRoot = permissionService.getServiceRoot();
        LambdaUpdateWrapper<Permission> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Permission::getParentId, serviceRoot.getId())
                .eq(Permission::getType, MenuType.Menu);
        permissionService.remove(wrapper);
        for (RouteRecord route : router) {
            try {
                routerFlatten(permissions, route, serviceRoot.getId());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        permissionService.saveBatch(permissions);
    }

    /**
     * 将路由树展平，便于保存
     */
    public void routerFlatten(List<Permission> permissions, RouteRecord route, String parentId) throws JsonProcessingException {
        Permission permission = new Permission()
                .setId(IdUtil.fastSimpleUUID())
                .setName(route.getName())
                .setPath(route.getPath())
                .setParentId(parentId)
                .setType(MenuType.Menu)
                .setComponent(route.getComponent())
                .setMeta(objectMapper.writeValueAsString(route.getMeta()));
        permissions.add(permission);
        if (Objects.nonNull(route.getChildren()) && !route.getChildren().isEmpty()) {
            for (RouteRecord child : route.getChildren()) {
                routerFlatten(permissions, child, permission.getId());
            }
        }
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
            if (permission.getType().equals(MenuType.Menu) && permission.getParentId().equals(parentNode.getId())) {
                RouteRecord record = new RouteRecord();
                record.setId(permission.getId())
                        .setPath(permission.getPath())
                        .setName(permission.getName())
                        .setComponent(permission.getComponent())
                        .setMeta(objectMapper.readValue(permission.getMeta(), RouteMeta.class));
                parentNode.getChildren().add(record);
                children(permissions, record);
                if (!parentNode.getChildren().isEmpty()) {
                    List<RouteRecord> notSortRouters = parentNode.getChildren().stream().filter(item -> Objects.isNull(item.getMeta().getSort())).toList();
                    List<RouteRecord> sortRouters = new LinkedList<>(parentNode.getChildren()
                            .stream()
                            .filter(item -> Objects.nonNull(item.getMeta().getSort()))
                            .toList());
                    if (!sortRouters.isEmpty()) {
                        sortRouters.sort(Comparator.comparing(item -> item.getMeta().getSort()));
                    }
                    if (!notSortRouters.isEmpty()) {
                        sortRouters.addAll(notSortRouters);
                    }
                    parentNode.setChildren(sortRouters);
                    parentNode.setRedirect(parentNode.getPath() + "/" + parentNode.getChildren().get(0).getPath());
                }
            }
        }
    }
}
