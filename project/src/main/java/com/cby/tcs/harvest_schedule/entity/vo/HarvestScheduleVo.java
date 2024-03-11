package com.cby.tcs.harvest_schedule.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class HarvestScheduleVo implements Serializable {

    /**
     * 调度编号
     */
    private String dispatchId;

    /**
     * 轧花厂名称
     */
    private String factoryName;

    /**
     * 轧花厂地址
     */
    private String addr;

    /**
     * 联系人
     */
    private String username;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 调度面积(亩)
     */
    private Double dispatchArea;

    /**
     * 主要品种
     */
    private String mainVarieties;

    /**
     * 调度单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime createTime;

}
