package com.cby.tcs.ginnery.entity.vo;

import com.cby.tcs.cotton_field.entity.vo.CottonFieldVo;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GinneryVo extends GinneryBasicVo implements Serializable {

    /**
     * 轧花厂下的地块
     */
    private List<CottonFieldVo> children;

}
