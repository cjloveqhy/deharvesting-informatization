package com.cby.tcs.ginnery.entity.vo;

import com.cby.tcs.user.entity.vo.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GinneryBasicVo implements Serializable {

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

}
