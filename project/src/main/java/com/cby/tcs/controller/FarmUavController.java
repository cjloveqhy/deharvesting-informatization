package com.cby.tcs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.farm_uav.entity.fo.FarmUavPageFo;
import com.cby.tcs.farm_uav.entity.vo.FarmUavVo;
import com.cby.tcs.farm_uav.service.FarmUavService;
import com.freedom.cloud.annotation.ParamsToEntity;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 农机管理-无人机 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/farm-uav")
@RequiredArgsConstructor
public class FarmUavController {

  private final FarmUavService farmUavService;

  /**
   * 分页过滤获取无人机的信息
   */
  @GetMapping("/filterPage")
  public ResultEntity filterPage(@ParamsToEntity(required = false) FarmUavPageFo entity) {
    Page<FarmUavVo> voPage = farmUavService.getFilterPage(entity);
    return ResultEntity.success(voPage);
  }

}
