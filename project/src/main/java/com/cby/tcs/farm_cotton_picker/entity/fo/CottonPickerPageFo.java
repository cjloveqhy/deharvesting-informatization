package com.cby.tcs.farm_cotton_picker.entity.fo;

import com.cby.tcs.farm_cotton_picker.entity.enums.HoldingAttrEnum;
import com.freedom.cloud.enums.LogicalEnum;
import com.freedom.cloud.utils.page.PageQuery;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CottonPickerPageFo extends PageQuery<CottonPickerPageFo> implements Serializable {

    private String brand;

    private String belonger;

    private HoldingAttrEnum holdingAttr;

    private LogicalEnum status;

    private String startTime;

    private String endTime;

}
