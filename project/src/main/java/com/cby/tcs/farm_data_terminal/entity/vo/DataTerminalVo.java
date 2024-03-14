package com.cby.tcs.farm_data_terminal.entity.vo;

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
public class DataTerminalVo implements Serializable {

    private String id;

    /**
     * 终端编号
     */
    private String terminalId;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 型号
     */
    private String model;

    /**
     * SIM卡到期时间
     */
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    private LocalDateTime simExpirationTime;

    /**
     * 关联记录id
     */
    private String joinId;

    /**
     * 是否关联农机
     */
    private LogicalEnum status = LogicalEnum.NO;

    /**
     * 采棉机机架号
     */
    private String cottonPickerRackId;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    private LocalDateTime updateTime;

}
