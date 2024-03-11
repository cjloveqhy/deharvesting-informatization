package com.cby.tcs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cby.tcs.harvest_schedule.service.HarvestScheduleService;
import org.springframework.web.bind.annotation.RestController;

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

  @PostMapping("/add")
  public ResultEntity add(@Validated @RequestBody AddHarvestScheduleFo entity){
    harvestScheduleService.add(entity);
    return ResultEntity.success("生成成功");
  }
}
