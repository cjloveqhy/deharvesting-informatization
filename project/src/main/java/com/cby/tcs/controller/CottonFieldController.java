package com.cby.tcs.controller;

import com.cby.tcs.cotton_field.entity.fo.CottonFieldByIdsFo;
import com.cby.tcs.cotton_field.entity.vo.CottonFieldVo;
import com.freedom.cloud.annotation.ParamsToEntity;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.cby.tcs.cotton_field.service.CottonFieldService;

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

  @PostMapping("/cottonsByIds")
  public ResultEntity getCottonsByIds(@RequestBody CottonFieldByIdsFo cottonFieldByIdsFo) {
    List<CottonFieldVo> cottons =  cottonFieldService.getCottonsByIds(cottonFieldByIdsFo);
    return ResultEntity.success(cottons);
  }
}
