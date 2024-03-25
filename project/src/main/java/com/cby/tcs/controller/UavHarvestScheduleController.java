package com.cby.tcs.controller;

import com.cby.tcs.uav_harvest_schedule.service.UavHarvestScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uav-harvest-schedule")
@RequiredArgsConstructor
public class UavHarvestScheduleController {

  private final UavHarvestScheduleService uavHarvestScheduleService;

}
