package com.cby.tcs.controller;

import com.cby.tcs.ginnery.entity.vo.GinneryVo;
import com.cby.tcs.ginnery.service.GinneryService;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ginnery")
@RequiredArgsConstructor
public class GinneryController {

  private final GinneryService ginneryService;

  /**
   * 通过轧花厂厂名搜索调度信息
   */
  @GetMapping("/search")
  public ResultEntity search(@RequestParam String factoryName) {
    List<GinneryVo> vos = ginneryService.search(factoryName);
    return ResultEntity.success(vos);
  }

}
