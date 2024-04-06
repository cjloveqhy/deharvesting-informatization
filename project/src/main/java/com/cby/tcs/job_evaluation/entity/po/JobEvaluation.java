package com.cby.tcs.job_evaluation.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.cby.tcs.job_evaluation.entity.enums.EvaluationResult;
import com.cby.tcs.job_evaluation.entity.enums.JobType;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 作业评价表
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("job_evaluation")
@EqualsAndHashCode(callSuper = true)
public class JobEvaluation extends Model<JobEvaluation> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 订单编号
     */
    @TableField("order_id")
    private String orderId;

    /**
     * 工作性质，飞手：U，采棉机：C
     */
    @TableField("job_type")
    private JobType jobType;

    /**
     * 工作id编号，若工作性质为飞手，则为飞手的ID编号，若为采棉机则为采棉机的编号
     */
    @TableField("job_id")
    private String jobId;

    /**
     * 棉田地id编号
     */
    @TableField("cotton_field_id")
    private String cottonFieldId;

    /**
     * 作业时间
     */
    @TableField("work_time")
    private LocalDateTime workTime;

    /**
     * 评价结果
     */
    @TableField("evaluation_result")
    private EvaluationResult evaluationResult;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer deleted;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;



}
