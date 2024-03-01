package com.cby.tcs.cotton_field.entity.po;

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
@TableName("cotton_field")
@EqualsAndHashCode(callSuper = true)
public class CottonField extends Model<CottonField> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 地块名称
     */
    @TableField("plot_name")
    private String plotName;

    /**
     * 轧花厂id
     */
    @TableField("ginnery_id")
    private String ginneryId;

    /**
     * 联系人
     */
    @TableField("contacts")
    private String contacts;

    /**
     * 种植面积(亩)
     */
    @TableField("cultivated_area")
    private Double cultivatedArea;

    /**
     * 棉花品种
     */
    @TableField("cotton_variety")
    private String cottonVariety;

    /**
     * 棉田地址
     */
    @TableField("addr")
    private String addr;

    /**
     * 逻辑删除
     */
    @TableField("deleted")
    private Boolean deleted;

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
