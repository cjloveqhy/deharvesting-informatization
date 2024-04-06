package com.cby.tcs.controller;

import com.cby.tcs.contract_terms.entity.enums.ContractTermsType;
import com.cby.tcs.contract_terms.entity.fo.SaveContractTermsFo;
import com.cby.tcs.contract_terms.entity.po.ContractTerms;
import com.cby.tcs.contract_terms.service.ContractTermsService;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contract-terms")
@RequiredArgsConstructor
public class ContractTermsController {

  private final ContractTermsService contractTermsService;

  /**
   * 获取合同条款信息
   */
  @GetMapping("/content")
  public ResultEntity getContent(@RequestParam ContractTermsType type) {
    ContractTerms contractTerms = contractTermsService.getContentByType(type);
    return ResultEntity.success(contractTerms);
  }

  /**
   * 保存合同条款
   */
  @PostMapping("/save")
  public ResultEntity save(@RequestBody SaveContractTermsFo entity) {
    contractTermsService.saveContractTerms(entity);
    return ResultEntity.success("保存成功");
  }

}
