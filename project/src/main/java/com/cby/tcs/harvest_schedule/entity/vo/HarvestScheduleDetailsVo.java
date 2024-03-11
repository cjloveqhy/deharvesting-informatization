package com.cby.tcs.harvest_schedule.entity.vo;

import com.cby.tcs.ginnery.entity.vo.GinneryVo;
import com.cby.tcs.user.entity.vo.UserInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class HarvestScheduleDetailsVo extends GinneryVo implements Serializable {

    private String id;

    /**
     * 联系人
     */
    private UserInfo contacts;

    /**
     * 轧花厂厂名
     */
    private String factoryName;

    /**
     * 地址
     */
    private String addr;

    /**
     * 加工能力(亩)
     */
    private Double workingAbility;

    /**
     * 调度面积（亩）
     */
    private Double dispatchingArea;

    /**
     * 调度编号
     */
    private String dispatchId;

    /**
     * 调度单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime createTime;

}
