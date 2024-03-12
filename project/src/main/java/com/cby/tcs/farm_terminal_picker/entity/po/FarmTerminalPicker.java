package com.cby.tcs.farm_terminal_picker.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据终端和采棉机的关联表
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("farm_terminal_picker")
@EqualsAndHashCode(callSuper = true)
public class FarmTerminalPicker extends Model<FarmTerminalPicker> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 是否关联农机
     */
    @TableField("status")
    private String status;

    /**
     * 采棉机机架号
     */
    @TableField("cotton_picker_rack_id")
    private String cottonPickerRackId;

    /**
     * 数据终端编号
     */
    @TableField("data_terminal_rack_id")
    private String dataTerminalRackId;

}
