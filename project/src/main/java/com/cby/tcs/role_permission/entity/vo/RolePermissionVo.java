package com.cby.tcs.role_permission.entity.vo;

import com.cby.tcs.role.entity.vo.RoleVo;
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
public class RolePermissionVo implements Serializable {

    private String id;

    /**
     * 角色id
     */
    private RoleVo role;

    /**
     * 权限集合
     */
    private List<String> permissions;

}
