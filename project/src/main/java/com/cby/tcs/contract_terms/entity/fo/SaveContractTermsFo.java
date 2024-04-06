package com.cby.tcs.contract_terms.entity.fo;

import com.cby.tcs.contract_terms.entity.enums.ContractTermsType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SaveContractTermsFo {

    private static final long serialVersionUID = 1L;

    /**
     * 条款类型
     */
    @NotNull(message = "条款类型不能为空")
    private ContractTermsType type;

    /**
     * 富文本正文内容
     */
    private String content;

}