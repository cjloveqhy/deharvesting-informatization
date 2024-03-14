package com.cby.tcs.farm_data_terminal.entity.fo;

import com.freedom.cloud.enums.LogicalEnum;
import com.freedom.cloud.utils.page.PageQuery;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DataTerminalPageFo extends PageQuery<DataTerminalPageFo> implements Serializable {

    /**
     * 终端编号
     */
    private String terminalId;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 是否关联农机
     */
    private LogicalEnum status;

    /**
     * 关联农机编号
     */
    private String cottonPickerRackId;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;
}
