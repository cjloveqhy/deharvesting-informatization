package com.cby.tcs.role.entity.fo;

import com.freedom.cloud.enums.LogicalEnum;
import com.freedom.cloud.utils.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RolePage extends PageQuery<RolePage> {

    /**
     * 角色名称
     */
    private String name;

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

}
