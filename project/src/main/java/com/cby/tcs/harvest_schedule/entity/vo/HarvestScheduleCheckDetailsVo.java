package com.cby.tcs.harvest_schedule.entity.vo;

import com.cby.tcs.cotton_field.entity.vo.CottonFieldVo;
import com.cby.tcs.job_evaluation.entity.vo.JobEvaluationPageVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class HarvestScheduleCheckDetailsVo extends CottonFieldVo implements Serializable {

    /**
     * 评价信息
     */
    private JobEvaluationPageVo jobEvaluation;
}