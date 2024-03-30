package com.cby.tcs.role.entity.fo;

import com.freedom.cloud.enums.LogicalEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

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

    /**
     * 角色标识
     */
    @NotBlank(message = "请输入角色标识")
    private String value;

    /**
     * 是否为默认角色
     */
    private LogicalEnum status = LogicalEnum.NO;

    /**
     * 是否禁用
     */
    private LogicalEnum disabled = LogicalEnum.NO;

    /**
     * 权限列表
     */
    private List<String> permissions = Collections.emptyList();

}
