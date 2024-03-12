package com.cby.tcs.farm_uav.entity.fo;

import com.cby.tcs.farm_cotton_picker.entity.enums.HoldingAttrEnum;
import com.freedom.cloud.utils.page.PageQuery;
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
public class FarmUavPageFo extends PageQuery<FarmUavPageFo> implements Serializable {

    /**
     * 机架号
     */
    private String rackNumber;

    /**
     * 所属服务商
     */
    private String serviceProvider;

    /**
     * 持有属性
     */
    private HoldingAttrEnum holdingAttr;

}
