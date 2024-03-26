package com.cby.tcs.controller;

import com.cby.tcs.uav_harvest_schedule.entity.fo.GetHsIdUavHarvestScheduleFo;
import com.cby.tcs.uav_harvest_schedule.service.UavHarvestScheduleService;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uav-harvest-schedule")
@RequiredArgsConstructor
public class UavHarvestScheduleController {

  private final UavHarvestScheduleService uavHarvestScheduleService;

  /**
   * 根据用户id和棉地id查询调度单id
   * @param entity
   * @return
   */
  @GetMapping("/get")
  public ResultEntity getDispatchId(GetHsIdUavHarvestScheduleFo entity) {
    String dispatchId = uavHarvestScheduleService.getDispatchId(entity);
    return ResultEntity.success(dispatchId);
  }
}
