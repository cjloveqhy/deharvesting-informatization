package com.cby.tcs.controller;

import com.cby.tcs.cotton_field.entity.vo.CottonFieldVo;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cby.tcs.cotton_field.service.CottonFieldService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cotton-field")
@RequiredArgsConstructor
public class CottonFieldController {

  private final CottonFieldService cottonFieldService;

  /**
   * 获取轧花厂地块信息
   */
  @GetMapping("/cottons")
  public ResultEntity getCottons(@RequestParam String factoryId) {
    List<CottonFieldVo> cottons =  cottonFieldService.getCottons(factoryId);
    return ResultEntity.success(cottons);
  }
}
