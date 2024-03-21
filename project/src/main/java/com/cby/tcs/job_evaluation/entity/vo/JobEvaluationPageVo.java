package com.cby.tcs.job_evaluation.entity.vo;

import com.cby.tcs.cotton_field.entity.vo.CottonFieldVo;
import com.cby.tcs.job_evaluation.entity.enums.EvaluationResult;
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
public class JobEvaluationPageVo implements Serializable {

    private String id;

    /**
     * 订单id编号
     */
    private String orderId;

    /**
     * 棉地信息
     */
    private CottonFieldVo cottonField;

    /**
     * 作业时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime workTime;

    /**
     * 评价结果
     */
    private EvaluationResult evaluationResult;

    /**
     * 若为飞手则为飞手信息，若为采棉机则为采棉机编号
     */
    private Object info;

}
