package com.cby.tcs.harvest_schedule.entity.fo;

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
public class FilterPageFo extends PageQuery<FilterPageFo> implements Serializable {

    private String ginneryName;

    private String dispatchId;

    private String contacts;

    private String startTime;

    private String endTime;

}
