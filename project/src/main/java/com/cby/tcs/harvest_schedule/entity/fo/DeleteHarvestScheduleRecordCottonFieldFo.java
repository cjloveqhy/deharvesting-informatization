package com.cby.tcs.harvest_schedule.entity.fo;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DeleteHarvestScheduleRecordCottonFieldFo implements Serializable {

    /**
     * 调度单编号
     */
    private String dispatchId;

    /**
     * 棉地id
     */
    private String cottonFieldId;
}
