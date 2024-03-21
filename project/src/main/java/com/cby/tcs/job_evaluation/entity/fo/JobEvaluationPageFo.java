package com.cby.tcs.job_evaluation.entity.fo;

import com.cby.tcs.job_evaluation.entity.enums.EvaluationResult;
import com.cby.tcs.job_evaluation.entity.enums.JobType;
import com.freedom.cloud.utils.page.PageQuery;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class JobEvaluationPageFo extends PageQuery<JobEvaluationPageFo> {

    /**
     * 订单id编号
     */
    private String orderId;

    /**
     * 工作性质
     */
    private JobType jobType;

    /**
     * 工作id编号
     */
    private String jobId;

    /**
     * 客户Id编号
     */
    private String customerId;

    /**
     * 是否合格
     */
    private EvaluationResult evaluationResult;

    private String startTime;

    private String endTime;

}
