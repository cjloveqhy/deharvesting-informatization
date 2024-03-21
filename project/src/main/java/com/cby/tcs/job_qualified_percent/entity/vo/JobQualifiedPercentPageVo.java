package com.cby.tcs.job_qualified_percent.entity.vo;

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
public class JobQualifiedPercentPageVo implements Serializable {

    private String id;

    /**
     * 作业面积亩）
     */
    private Double area;

    /**
     * 累计时长（作业时长）（小时）
     */
    private Double cumulativeDuration;

    /**
     * 合格率（%）
     */
    private Double percentPass;

    /**
     * 若为飞手则为飞手信息，若为采棉机则为采棉机编号
     */
    private Object info;
}
