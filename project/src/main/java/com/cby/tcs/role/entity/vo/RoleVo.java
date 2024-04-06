package com.cby.tcs.role.entity.vo;

import com.freedom.cloud.enums.LogicalEnum;
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
public class RoleVo implements Serializable {

    private String id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色优先级
     */
    private Integer level;

    /**
     * 备注
     */
    private String remark;

    /**
     * 角色标识
     */
    private String value;

    /**
     * 是否为默认角色
     */
    private LogicalEnum status;

    /**
     * 是否禁用
     */
    private LogicalEnum disabled;

    /**
     * 权限列表
     */
    private List<String> permissions;

}
