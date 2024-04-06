package com.cby.tcs.contract_terms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.contract_terms.dao.ContractTermsDao;
import com.cby.tcs.contract_terms.entity.po.ContractTerms;
import com.cby.tcs.contract_terms.service.ContractTermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractTermsServiceImpl extends ServiceImpl<ContractTermsDao, ContractTerms> implements ContractTermsService {

  private final ContractTermsDao contract_termsDao;
}
