package com.cby.tcs.contract_terms.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ContractTermsType {

    CottonGrower("C", "棉农"),
    Uav("U", "飞手"),
    Driver("D", "司机"),
    Ginnery("G", "轧花厂"),
    TestingUnit("T", "检测单位");

    @EnumValue
    @JsonValue
    private String code;

    private String description;

    ContractTermsType(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
