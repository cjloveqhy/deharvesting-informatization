package com.cby.tcs.controller;

import com.cby.tcs.contract_terms.service.ContractTermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contract-terms")
@RequiredArgsConstructor
public class ContractTermsController {

  private final ContractTermsService contractTermsService;

}
