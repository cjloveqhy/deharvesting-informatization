package com.cby.tcs.farm_cotton_picker.entity.vo;

import com.cby.tcs.farm_cotton_picker.entity.enums.HoldingAttrEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.freedom.cloud.enums.LogicalEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CottonPickerVo implements Serializable {

    private String id;

    /**
     * 农机编号
     */
    private String rackNumber;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 归属者
     */
    private String belonger;

    /**
     * 归属者姓名
     */
    private String username;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 持有属性
     */
    private HoldingAttrEnum holdingAttr;

    /**
     * 所属公司
     */
    private String affiliatedCompany;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 是否关联农机
     */
    private LogicalEnum status = LogicalEnum.NO;

    /**
     * 关联数据终端编号
     */
    private String dataTerminalRackId;


}
