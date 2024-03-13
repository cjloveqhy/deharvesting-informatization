package com.cby.tcs.farm_cotton_picker.entity.fo;

import com.cby.tcs.farm_cotton_picker.entity.enums.HoldingAttrEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CottonPickerFo implements Serializable {

    private String id;

    /**
     * 农机编号
     */
    @NotBlank(message = "请输入农机编号")
    private String rackNumber;

    /**
     * 品牌
     */
    @NotBlank(message = "请输入品牌")
    private String brand;

    /**
     * 归属者
     */
    @NotBlank(message = "请选择归属者")
    private String belonger;

    /**
     * 持有属性
     */
    @NotNull(message = "请选择持有属性")
    private HoldingAttrEnum holdingAttr;

    /**
     * 所属公司
     */
    private String affiliatedCompany;

}
