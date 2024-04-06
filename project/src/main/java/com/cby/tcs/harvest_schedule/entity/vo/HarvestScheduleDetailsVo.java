package com.cby.tcs.harvest_schedule.entity.vo;

import com.cby.tcs.farm_uav.entity.vo.FarmUavBelongerVo;
import com.cby.tcs.ginnery.entity.vo.GinneryVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class HarvestScheduleDetailsVo extends GinneryVo implements Serializable {

    /**
     * 调度面积（亩）
     */
    private Double dispatchArea;

    /**
     * 调度编号
     */
    private String dispatchId;

    /**
     * 飞手信息
     */
    private FarmUavBelongerVo farmUavBelongerInfo;

    /**
     * 调度单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime createTime;

}
