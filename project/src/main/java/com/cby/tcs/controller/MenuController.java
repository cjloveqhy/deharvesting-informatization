package com.cby.tcs.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.cby.tcs.menu.entity.RouteRecord;
import com.cby.tcs.menu.service.MenuService;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    /**
     * 获取菜单
     */
    @GetMapping("/")
    public ResultEntity getMenu() {
        List<RouteRecord> menus = menuService.getMenu(StpUtil.getLoginId().toString());
        return ResultEntity.success(menus);
    }
}
