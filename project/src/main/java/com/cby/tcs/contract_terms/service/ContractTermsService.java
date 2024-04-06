package com.cby.tcs.contract_terms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cby.tcs.contract_terms.entity.enums.ContractTermsType;
import com.cby.tcs.contract_terms.entity.fo.SaveContractTermsFo;
import com.cby.tcs.contract_terms.entity.po.ContractTerms;

public interface ContractTermsService extends IService<ContractTerms> {

    /**
     * 按照条款类型保存条款内容
     * @param entity {@link SaveContractTermsFo} 信息实体
     */
    void saveContractTerms(SaveContractTermsFo entity);

    /**
     * 根据条款类型获取条款内容
     * @param type 条款类型
     * @return {@link ContractTerms}
     */
    ContractTerms getContentByType(ContractTermsType type);
}
