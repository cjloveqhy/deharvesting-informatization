package com.cby.tcs.farm_cotton_picker.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.cby.tcs.farm_cotton_picker.entity.enums.HoldingAttrEnum;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 农机管理-采棉机
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("farm_cotton_picker")
@EqualsAndHashCode(callSuper = true)
public class FarmCottonPicker extends Model<FarmCottonPicker> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 农机编号
     */
    @TableField("rack_number")
    private String rackNumber;

    /**
     * 品牌
     */
    @TableField("brand")
    private String brand;

    /**
     * 归属者
     */
    @TableField("belonger")
    private String belonger;

    /**
     * 持有属性
     */
    @TableField("holding_attr")
    private HoldingAttrEnum holdingAttr;

    /**
     * 所属公司
     */
    @TableField(value = "affiliated_company", updateStrategy = FieldStrategy.IGNORED)
    private String affiliatedCompany;

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
