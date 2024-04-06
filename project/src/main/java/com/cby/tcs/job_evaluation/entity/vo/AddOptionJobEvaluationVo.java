package com.cby.tcs.job_evaluation.entity.vo;

import com.freedom.cloud.options.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AddOptionJobEvaluationVo implements Serializable {
    /**
     * 地块名称option
     */
    List<Option> plotNameOption;

    /**
     * 客户名称option
     */
    List<Option> userNameOption;

    /**
     * 飞手名称option
     */
    List<Option> uavNameOption;

    /**
     * 采棉机名称option
     */
    List<Option> cottonFieldNameOption;
}
