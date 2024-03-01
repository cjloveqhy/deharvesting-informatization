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

}
