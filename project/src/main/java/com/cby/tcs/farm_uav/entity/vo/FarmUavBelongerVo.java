package com.cby.tcs.farm_uav.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FarmUavBelongerVo extends FarmUavVo {
    /**
     * 飞手则为飞手信息
     */
    private Object info;
}
