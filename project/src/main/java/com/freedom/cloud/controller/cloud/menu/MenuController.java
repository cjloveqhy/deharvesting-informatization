package com.freedom.cloud.controller.cloud.menu;

import com.freedom.cloud.api.menu.entity.RouteRecord;
import com.freedom.cloud.api.menu.service.MenuService;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cjloveqhy
 * @since 2024-01-31
 */
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    @Value("${spring.application.name}")
    private String serverTag;

    private final MenuService menuService;

    /**
     * 获取菜单
     */
    @GetMapping("/")
    public ResultEntity getMenu() {
        List<RouteRecord> menu = menuService.getMenu(serverTag);
        return ResultEntity.success(menu);
    }
}
