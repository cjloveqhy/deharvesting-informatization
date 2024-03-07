package com.cby.tcs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.ginnery.entity.fo.GinneryPageFo;
import com.cby.tcs.ginnery.entity.vo.GinneryVo;
import com.cby.tcs.ginnery.service.GinneryService;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ginnery")
@RequiredArgsConstructor
public class GinneryController {

  private final GinneryService ginneryService;

  /**
   * 通过轧花厂厂名搜索调度信息
   */
  @PostMapping("/search")
  public ResultEntity search(@RequestBody GinneryPageFo entity) {
    Page<GinneryVo> vos = ginneryService.search(entity);
    return ResultEntity.success(vos);
  }

}
