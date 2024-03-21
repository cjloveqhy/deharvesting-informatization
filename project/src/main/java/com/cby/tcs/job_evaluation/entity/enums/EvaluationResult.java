package com.cby.tcs.job_evaluation.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 评价结果枚举
 */
public enum EvaluationResult {

    Pass_Qualified("PQ", "合格"),

    NOT_Qualified("NQ", "不合格");

    @EnumValue
    @JsonValue
    private String code;

    private String description;

    EvaluationResult(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
