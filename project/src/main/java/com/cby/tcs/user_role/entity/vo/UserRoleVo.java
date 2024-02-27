package com.cby.tcs.user_role.entity.vo;

import com.cby.tcs.role.entity.vo.RoleVo;
import com.cby.tcs.user.entity.vo.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserRoleVo implements Serializable {

    private String id;

    /**
     * 用户id
     */
    private UserInfo userId;

    /**
     * 角色id
     */
    private RoleVo roleId;

    /**
     * 额外权限，当角色权限不可满足时，可通过此属性进行添加
     */
    private List<String> attachedPermission;
}
