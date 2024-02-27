package com.cby.tcs.role.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
}
