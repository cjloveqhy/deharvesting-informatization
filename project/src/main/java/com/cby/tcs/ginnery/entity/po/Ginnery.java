package com.cby.tcs.ginnery.entity.po;

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
@TableName("ginnery")
@EqualsAndHashCode(callSuper = true)
public class Ginnery extends Model<Ginnery> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 联系人
     */
    @TableField("contacts")
    private String contacts;

    /**
     * 轧花厂厂名
     */
    @TableField("factory_name")
    private String factoryName;

    /**
     * 地址
     */
    @TableField("addr")
    private String addr;

    /**
     * 加工能力(亩)
     */
    @TableField("working_ability")
    private Double workingAbility;

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
