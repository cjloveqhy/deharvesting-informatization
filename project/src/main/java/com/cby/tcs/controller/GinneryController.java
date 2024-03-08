package com.cby.tcs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.ginnery.entity.fo.GinneryPageFo;
import com.cby.tcs.ginnery.entity.vo.GinneryVo;
import com.cby.tcs.ginnery.service.GinneryService;
import com.freedom.cloud.annotation.ParamsToEntity;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ginnery")
@RequiredArgsConstructor
public class GinneryController {

  private final GinneryService ginneryService;

  /**
   * 通过轧花厂厂名搜索调度信息
   */
  @GetMapping("/search")
  public ResultEntity search(@ParamsToEntity(params = {@RequestParam(value = "isMax", required = false)}) GinneryPageFo entity) {
    Page<GinneryVo> vos = ginneryService.search(entity);
    return ResultEntity.success(vos);
  }

}
