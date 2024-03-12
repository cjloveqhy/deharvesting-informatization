package com.cby.tcs.farm_cotton_picker.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 农机持有属性
 */
public enum HoldingAttrEnum {

    SelfOwned("S", "自有"),

    Lease("L", "租赁");

    @EnumValue
    @JsonValue
    private String code;

    private String description;

    HoldingAttrEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
