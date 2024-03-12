package com.cby.tcs.harvest_schedule.entity.vo;

import com.cby.tcs.cotton_field.entity.po.CottonField;
import com.cby.tcs.cotton_field.entity.vo.CottonFieldVo;
import com.cby.tcs.ginnery.entity.vo.GinneryVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class HarvestScheduleRecordVo implements Serializable {
    /**
     * 调度编号
     */
    private String dispatchId;

    /**
     * 轧花厂信息 和 棉地信息
     */
    private GinneryVo ginnery;

    /**
     * 调度单内含有的棉地信息
     */
    private List<CottonFieldVo> cottonFields;

    /**
     * 调度单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime createTime;

}
