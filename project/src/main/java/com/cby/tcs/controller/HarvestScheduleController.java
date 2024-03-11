package com.cby.tcs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.harvest_schedule.entity.fo.AddHarvestScheduleFo;
import com.cby.tcs.harvest_schedule.entity.fo.FilterPageFo;
import com.cby.tcs.harvest_schedule.entity.vo.HarvestScheduleVo;
import com.freedom.cloud.annotation.ParamsToEntity;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.cby.tcs.harvest_schedule.service.HarvestScheduleService;

@RestController
@RequestMapping("/harvest-schedule")
@RequiredArgsConstructor
public class HarvestScheduleController {

  private final HarvestScheduleService harvestScheduleService;

  /**
   * 获取过滤分页数据
   */
  @GetMapping("/filterPage")
  public ResultEntity filterPage(@ParamsToEntity(required = false, exclude = {"ginneryId"}) FilterPageFo entity) {
    Page<HarvestScheduleVo> vos = harvestScheduleService.filterPage(entity);
    return ResultEntity.success(vos);
  }

  /**
   * 创建调度单记录
   */
  @PostMapping("/add")
  public ResultEntity add(@Validated @RequestBody AddHarvestScheduleFo entity){
    harvestScheduleService.add(entity);
    return ResultEntity.success("生成成功");
  }
}
