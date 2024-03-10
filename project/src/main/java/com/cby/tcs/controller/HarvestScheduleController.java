package com.cby.tcs.controller;

import com.cby.tcs.harvest_schedule.entity.fo.AddHarvestScheduleFo;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cby.tcs.harvest_schedule.service.HarvestScheduleService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/harvest-schedule")
@RequiredArgsConstructor
public class HarvestScheduleController {

  private final HarvestScheduleService harvestScheduleService;

  @PostMapping("/add")
  public ResultEntity add(@Validated @RequestBody AddHarvestScheduleFo entity){
    harvestScheduleService.add(entity);
    return ResultEntity.success("生成成功");
  }
}
