package com.cby.tcs.user_role.entity.fo;

import jakarta.validation.constraints.NotBlank;
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
public class UpdateUserRoleFo implements Serializable {

    @NotBlank(message = "id编号不可为空")
    private String id;

    /**
     * 用户id
     */
    @NotBlank(message = "请选择用户")
    private String userId;

    /**
     * 角色id
     */
    @NotBlank(message = "请选择角色")
    private String roleId;

    /**
     * 额外权限，当角色权限不可满足时，可通过此属性进行添加
     */
    private List<String> attachedPermission;

}
