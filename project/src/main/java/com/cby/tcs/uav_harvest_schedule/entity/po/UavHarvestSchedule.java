package com.cby.tcs.uav_harvest_schedule.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("uav_harvest_schedule")
@EqualsAndHashCode(callSuper = true)
public class UavHarvestSchedule extends Model<UavHarvestSchedule> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 调度单的id编号
     */
    @TableField("hs_id")
    private String hsId;

    /**
     * 飞手的id编号
     */
    @TableField("belonger")
    private String belonger;



}
