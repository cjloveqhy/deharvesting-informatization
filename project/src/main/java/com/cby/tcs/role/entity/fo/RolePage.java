package com.cby.tcs.role.entity.fo;

import com.freedom.cloud.utils.page.PageQuery;
import lombok.Data;

@Data
public class RolePage extends PageQuery<RolePage> {

    /**
     * 角色名称
     */
    private String name;

}
