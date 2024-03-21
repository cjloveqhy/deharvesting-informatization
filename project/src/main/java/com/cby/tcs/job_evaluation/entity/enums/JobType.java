package com.cby.tcs.job_evaluation.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 工作性质枚举
 */
public enum JobType {

    Uav("U", "无人机"),

    Cotton_Picker("C", "采棉机");

    @EnumValue
    @JsonValue
    private String code;

    private String description;

    JobType(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
