package com.cby.tcs.contract_terms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.contract_terms.dao.ContractTermsDao;
import com.cby.tcs.contract_terms.entity.enums.ContractTermsType;
import com.cby.tcs.contract_terms.entity.fo.SaveContractTermsFo;
import com.cby.tcs.contract_terms.entity.po.ContractTerms;
import com.cby.tcs.contract_terms.service.ContractTermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContractTermsServiceImpl extends ServiceImpl<ContractTermsDao, ContractTerms> implements ContractTermsService {

  private final ContractTermsDao contractTermsDao;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void saveContractTerms(SaveContractTermsFo entity) {
    LambdaQueryWrapper<ContractTerms> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(ContractTerms::getType, entity.getType());
    ContractTerms contractTerms = getOne(wrapper);
    if (Objects.nonNull(contractTerms)) {
      updateById(contractTerms.setContent(entity.getContent()));
    } else {
      contractTerms = BeanUtil.copyProperties(entity, ContractTerms.class);
      save(contractTerms);
    }
  }

  @Override
  public ContractTerms getContentByType(ContractTermsType type) {
    LambdaQueryWrapper<ContractTerms> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(ContractTerms::getType, type);
    return getOne(wrapper);
  }

}
