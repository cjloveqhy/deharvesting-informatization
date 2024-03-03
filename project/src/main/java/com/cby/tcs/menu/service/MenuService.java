package com.cby.tcs.menu.service;


import com.cby.tcs.menu.entity.RouteRecord;

import java.util.List;

public interface MenuService {

    /**
     * 根据用户id编号获取菜单
     * @param serverTag 服务标签
     * @param userId 用户id编号
     * @return {@link List}<{@link RouteRecord}>
     */
    List<RouteRecord> getMenu(String userId);

    /**
     * 同步更新菜单
     * @param router {@link List}<{@link RouteRecord}>
     */
    void updateMenu(List<RouteRecord> router);
}
