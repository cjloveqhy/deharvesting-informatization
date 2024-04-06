package com.cby.tcs.menu.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cby.tcs.menu.entity.RouteMeta;
import com.cby.tcs.menu.entity.RouteRecord;
import com.cby.tcs.menu.service.MenuService;
import com.cby.tcs.permission.entity.po.Permission;
import com.cby.tcs.permission.service.PermissionService;
import com.cby.tcs.role_permission.service.RolePermissionService;
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
        List<String> permissionIds = userRoleService.getRolePermissionIds(userId);
        List<Permission> permissions = permissionService.getList();
        Set<String> currenUserPermissions = new HashSet<>();
        for (Permission permission : permissions) {
            if (permissionIds.contains(permission.getId())) {
                RouteMeta routeMeta = objectMapper.readValue(permission.getMeta(), RouteMeta.class);
                if (Objects.nonNull(routeMeta.getPermissions()) && !routeMeta.getPermissions().isEmpty()) {
                    currenUserPermissions.addAll(routeMeta.getPermissions());
                }
            }
        }
        Permission root = permissionService.getAndDelServiceNode(permissions);
        if (Objects.isNull(root)) {
            return Collections.emptyList();
        }
        for (int i = 0; i < permissions.size(); i++) {
            Permission permission = permissions.get(i);
            RouteMeta routeMeta = objectMapper.readValue(permission.getMeta(), RouteMeta.class);
            List<String> list = routeMeta.getPermissions();
            if (Objects.nonNull(list) && !list.isEmpty()) {
                boolean match = list.stream().anyMatch(currenUserPermissions::contains);
                if (!match) {
                    permissions.remove(permission);
                    i--;
                }
            }
        }
        List<Permission> roleNode = getRoleNode(permissions, root);
        if (roleNode.isEmpty()) return Collections.emptyList();
        List<RouteRecord> firstNode = getFirstNode(roleNode, root);
        for (RouteRecord record : firstNode) {
            children(roleNode, record);
        }
        return firstNode;
    }

    private List<Permission> getRoleNode(List<Permission> permissions, Permission root) {
        List<Permission> newPermissions = new ArrayList<>();
        List<String> parentIds = permissions.stream()
                .filter(item -> List.of(MenuType.Button, MenuType.Content).contains(item.getType()))
                .map(Permission::getParentId)
                .distinct()
                .toList();
        for (Permission permission : permissions) {
            if (parentIds.contains(permission.getId())) {
                if (!newPermissions.contains(permission)) newPermissions.add(permission);
                getParentNode(permissions, permission, root, newPermissions);
            }
        }
        return newPermissions;
    }

    public void getParentNode(List<Permission> permissions, Permission node, Permission root, List<Permission> newPermissions) {
        if (!parentIsRoot(node, root)) {
            String parentId = node.getParentId();
            for (Permission permission : permissions) {
                if (permission.getId().equals(parentId)) {
                    if (!newPermissions.contains(permission)) newPermissions.add(permission);
                    getParentNode(permissions, permission, root, newPermissions);
                }
            }
        }
    }

    public boolean parentIsRoot(Permission node, Permission root) {
        return node.getParentId().equals(root.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(List<RouteRecord> router) {
        if (Objects.isNull(router) || router.isEmpty()) return;
        List<Permission> permissions = new ArrayList<>();
        Permission serviceRoot = permissionService.getServiceRoot();
        LambdaUpdateWrapper<Permission> wrapper = new LambdaUpdateWrapper<>();
        wrapper.ne(Permission::getType, MenuType.Service);
        permissionService.remove(wrapper);
        for (RouteRecord route : router) {
            try {
                routerFlatten(permissions, route, serviceRoot.getId(), null);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        permissionService.saveBatch(permissions);
    }

    /**
     * 将路由树展平，便于保存
     */
    public void routerFlatten(List<Permission> permissions, RouteRecord route, String parentId, Integer sort) throws JsonProcessingException {
        if (Objects.isNull(route.getMeta().getSort())) route.getMeta().setSort(sort);
        MenuType menuType = Objects.isNull(route.getMeta().getType()) ? MenuType.Menu : route.getMeta().getType();
        if (menuType.equals(MenuType.Menu)) route.getMeta().setAlwaysShow(true);
        Permission permission = new Permission()
                .setId(IdUtil.fastSimpleUUID())
                .setName(route.getName())
                .setPath(route.getPath())
                .setParentId(parentId)
                .setType(menuType)
                .setComponent(route.getComponent())
                .setMeta(objectMapper.writeValueAsString(route.getMeta()));
        permissions.add(permission);
        if (Objects.nonNull(route.getChildren()) && !route.getChildren().isEmpty()) {
            int index = 1;
            for (RouteRecord child : route.getChildren()) {
                routerFlatten(permissions, child, permission.getId(), index);
                index++;
            }
        }
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
