package com.cby.tcs.cotton_field.entity.fo;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CottonFieldByIdsFo implements Serializable {
    private String ginneryId;
    private List<String> cottonFieldIds;
}
