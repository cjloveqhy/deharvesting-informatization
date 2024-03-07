package com.cby.tcs.cotton_field.entity.vo;

import com.cby.tcs.ginnery.entity.vo.GinneryBasicVo;
import com.cby.tcs.user.entity.vo.UserInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CottonFieldVo implements Serializable {

    private String id;

    /**
     * 地块名称
     */
    private String plotName;

    /**
     * 轧花厂
     */
    private GinneryBasicVo ginnery;

    /**
     * 联系人
     */
    private UserInfo contacts;

    /**
     * 种植面积(亩)
     */
    private Double cultivatedArea;

    /**
     * 棉花品种
     */
    private String cottonVariety;

    /**
     * 棉田地址
     */
    private String addr;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime createTime;

}
