package com.cby.tcs.role_permission.entity.fo;

import com.freedom.cloud.utils.page.PageQuery;
import lombok.Data;

import java.util.List;

@Data
public class RolePermissionPage extends PageQuery<RolePermissionPage> {

    /**
     * 角色id集合
     */
    private List<String> roleIds;

    /**
     * 权限id集合
     */
    private List<String> permissions;

}
