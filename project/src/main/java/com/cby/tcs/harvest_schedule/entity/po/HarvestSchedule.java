package com.cby.tcs.harvest_schedule.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("harvest_schedule")
@EqualsAndHashCode(callSuper = true)
public class HarvestSchedule extends Model<HarvestSchedule> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 调度编号
     */
    @TableField("dispatch_id")
    private String dispatchId;

    /**
     * 创建者
     */
    @TableField("creator")
    private String creator;

    /**
     * 轧花厂id
     */
    @TableField("ginnery_id")
    private String ginneryId;

    /**
     * 调度棉地的id集合
     */
    @TableField("cotton_field_id")
    private String cottonFieldId;

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
