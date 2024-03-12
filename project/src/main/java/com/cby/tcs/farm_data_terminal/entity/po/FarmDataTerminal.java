package com.cby.tcs.farm_data_terminal.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 农机管理-数据终端
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("farm_data_terminal")
@EqualsAndHashCode(callSuper = true)
public class FarmDataTerminal extends Model<FarmDataTerminal> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 终端编号
     */
    @TableField("terminal_id")
    private String terminalId;

    /**
     * 品牌
     */
    @TableField("brand")
    private String brand;

    /**
     * 型号
     */
    @TableField("model")
    private String model;

    /**
     * SIM卡到期时间
     */
    @TableField("sim_expiration_time")
    private LocalDateTime simExpirationTime;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer deleted;

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
