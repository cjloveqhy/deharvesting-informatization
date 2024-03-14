package com.cby.tcs.farm_data_terminal.entity.fo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.freedom.cloud.enums.LogicalEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DataTerminalFo implements Serializable {

    private String id;

    /**
     * 终端编号
     */
    @NotBlank(message = "请输入终端编号")
    private String terminalId;

    /**
     * 品牌
     */
    @NotBlank(message = "请输入品牌")
    private String brand;

    /**
     * 型号
     */
    @NotBlank(message = "请输入型号")
    private String model;

    /**
     * SIM卡到期时间
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate simExpirationTime;

    /**
     * 是否关联农机
     */
    private LogicalEnum status;

    /**
     * 关联农机编号
     */
    private String cottonPickerRackId;

}
