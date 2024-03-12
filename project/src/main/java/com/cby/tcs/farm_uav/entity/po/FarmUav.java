package com.cby.tcs.farm_uav.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.cby.tcs.farm_cotton_picker.entity.enums.HoldingAttrEnum;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 农机管理-无人机
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("farm_uav")
@EqualsAndHashCode(callSuper = true)
public class FarmUav extends Model<FarmUav> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 机架号
     */
    @TableField("rack_number")
    private String rackNumber;

    /**
     * 所属服务商
     */
    @TableField("service_provider")
    private String serviceProvider;

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
     * 作业面积(亩)
     */
    @TableField("work_area")
    private Double workArea;

    /**
     * 作业时间(小时)
     */
    @TableField("work_time")
    private Double workTime;

    /**
     * 喷洒流量(升/亩)
     */
    @TableField("spray_flow_rate")
    private Double sprayFlowRate;

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
