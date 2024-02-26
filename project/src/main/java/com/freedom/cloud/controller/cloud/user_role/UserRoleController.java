package com.freedom.cloud.controller.cloud.user_role;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.freedom.cloud.api.user_role.entity.fo.AddUserRoleFo;
import com.freedom.cloud.api.user_role.entity.fo.UpdateUserRoleFo;
import com.freedom.cloud.api.user_role.entity.fo.UserRolePage;
import com.freedom.cloud.api.user_role.entity.vo.UserRoleVo;
import com.freedom.cloud.api.user_role.service.UserRoleService;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/user-role")
@RequiredArgsConstructor
public class UserRoleController {

    @Value("${spring.application.name}")
    private String serverTag;

    private final UserRoleService userRoleService;

    /**
     * 获取菜单
     */
    @GetMapping("/rolePermissions")
    public ResultEntity getRolePermissions() {
        List<String> permissions = userRoleService.getRolePermissions(serverTag, (String) StpUtil.getLoginId());
        return ResultEntity.success(permissions);
    }

    /**
     * 分页获取用户角色权限
     */
    @PostMapping("/page")
    public ResultEntity getUserRolePage(@RequestBody UserRolePage entity) {
        Page<UserRoleVo> page = userRoleService.getUserRolePage(serverTag, entity);
        return ResultEntity.success(page);
    }

    /**
     * 添加用户角色权限
     */
    @PostMapping("/add")
    public ResultEntity addUserRole(@Validated @RequestBody AddUserRoleFo entity) {
        userRoleService.addUserRole(serverTag, entity);
        return ResultEntity.success("添加成功");
    }

    /**
     * 修改用户角色权限
     */
    @PutMapping("/update")
    public ResultEntity updateUserRole(@Validated @RequestBody UpdateUserRoleFo entity) {
        userRoleService.updateUserRole(serverTag, entity);
        return ResultEntity.success("更新成功");
    }

    /**
     * 删除用户角色权限
     */
    @DeleteMapping("/del")
    public ResultEntity delUserRole(@RequestParam String id) {
        userRoleService.delUserRole(serverTag, id);
        return ResultEntity.success("删除成功");
    }

}
