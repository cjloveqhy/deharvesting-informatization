package com.cby.tcs.uav_harvest_schedule.entity.fo;

import jakarta.validation.constraints.NotBlank;
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
public class GetHsIdUavHarvestScheduleFo implements Serializable {

    /**
     * 所属人id
     */
    @NotBlank(message = "所属人id为空")
    private String belonger;

    /**
     * 棉田地id编号
     */
    private String cottonFieldId;
}
