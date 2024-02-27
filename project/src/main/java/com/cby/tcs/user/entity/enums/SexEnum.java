package com.cby.tcs.user.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum SexEnum {

    Man("M", "男性"),

    Woman("W", "女性"),

    Unknown("U", "未知");

    @Getter
    @EnumValue
    private final String code;

    @Getter
    @JsonValue
    private final String description;

    SexEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
