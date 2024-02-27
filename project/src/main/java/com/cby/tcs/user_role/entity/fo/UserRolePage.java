package com.cby.tcs.user_role.entity.fo;

import com.freedom.cloud.utils.page.PageQuery;
import lombok.Data;

import java.util.List;

@Data
public class UserRolePage extends PageQuery<UserRolePage> {

    /**
     * 用户id编号
     */
    private List<String> userIds;

    /**
     * 角色id
     */
    private List<String> roleIds;

    /**
     * 额外权限，当角色权限不可满足时，可通过此属性进行添加
     */
    private List<String> attachedPermissions;
}
