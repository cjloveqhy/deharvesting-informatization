package com.cby.tcs.role_permission.entity.fo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class AddRolePermissionFo implements Serializable {

    @NotBlank(message = "请选择角色")
    private String roleId;

    /**
     * 权限集合
     */
    @NotEmpty(message = "请配置角色权限列表")
    private List<String> permissions;

}
