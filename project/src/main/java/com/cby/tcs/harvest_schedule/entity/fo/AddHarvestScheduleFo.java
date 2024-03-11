package com.cby.tcs.harvest_schedule.entity.fo;

import com.baomidou.mybatisplus.annotation.TableField;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AddHarvestScheduleFo implements Serializable {

    /**
     * 轧花厂id
     */
    @NotBlank(message = "轧花厂编号为空")
    private String ginneryId;

    /**
     * 调度棉地的id
     */
    private String cottonFieldId;
}
