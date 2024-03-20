package com.cby.tcs.job_qualified_percent.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 作业合格率总览表
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("job_qualified_percent")
@EqualsAndHashCode(callSuper = true)
public class JobQualifiedPercent extends Model<JobQualifiedPercent> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 工作性质，飞手：U，采棉机：C
     */
    @TableField("job_type")
    private String jobType;

    /**
     * 工作id编号，若工作性质为飞手，则为飞手的ID编号，若为采棉机则为采棉机的编号
     */
    @TableField("job_id")
    private String jobId;

    /**
     * 作业面积		（亩）
     */
    @TableField("area")
    private Double area;

    /**
     * 累计时长（作业时长）（小时）
     */
    @TableField("cumulative_duration")
    private Double cumulativeDuration;

    /**
     * 合格率（%）
     */
    @TableField("percent_pass")
    private Double percentPass;

    /**
     * 逻辑删除
     */
    @TableField("deleted")
    private Boolean deleted;

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
