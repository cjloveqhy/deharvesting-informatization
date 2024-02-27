package com.cby.tcs.role.entity.fo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UpdateRole implements Serializable {

    @NotBlank(message = "id编号不可为空")
    private String id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不可为空")
    private String name;

    /**
     * 角色优先级
     */
    @Range(min = 0, max = 9, message = "请输入正确的优先级，level：1-9")
    private Integer level;

    /**
     * 备注
     */
    private String remark;

}
