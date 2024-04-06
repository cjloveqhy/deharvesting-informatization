package com.cby.tcs.job_qualified_percent.entity.fo;

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
public class JobQualifiedPercentPageFo extends PageQuery<JobQualifiedPercentPageFo> {

    /**
     * 工作性质
     */
    private JobType jobType;

}
