package com.cby.tcs.job_evaluation.entity.fo;

import com.cby.tcs.job_evaluation.entity.enums.EvaluationResult;
import com.cby.tcs.job_evaluation.entity.enums.JobType;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class AddJobEvaluationFo implements Serializable {
    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 地块名称
     */
    private String cottonFieldId;

    /**
     * 作业时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String workTime;

    /**
     * 评价结果
     */
    private EvaluationResult evaluationResult;

    /**
     * 工作性质，飞手：U，采棉机：C
     */
    private JobType jobType;

    /**
     * 工作id编号，若工作性质为飞手，则为飞手的ID编号，若为采棉机则为采棉机的编号
     */
    private String jobId;

}
