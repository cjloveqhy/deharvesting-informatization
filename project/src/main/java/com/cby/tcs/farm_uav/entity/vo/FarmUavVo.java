package com.cby.tcs.farm_uav.entity.vo;

import com.cby.tcs.farm_cotton_picker.entity.enums.HoldingAttrEnum;
import com.cby.tcs.user.entity.vo.UserInfo;
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
public class FarmUavVo implements Serializable {

    private String id;

    /**
     * 机架号
     */
    private String rackNumber;

    /**
     * 所属服务商
     */
    private String serviceProvider;

    /**
     * 归属者
     */
    private UserInfo belonger;

    /**
     * 持有属性
     */
    private HoldingAttrEnum holdingAttr;

    /**
     * 作业面积(亩)
     */
    private Double workArea;

    /**
     * 作业时间(小时)
     */
    private Double workTime;

    /**
     * 喷洒流量(升/亩)
     */
    private Double sprayFlowRate;

}
