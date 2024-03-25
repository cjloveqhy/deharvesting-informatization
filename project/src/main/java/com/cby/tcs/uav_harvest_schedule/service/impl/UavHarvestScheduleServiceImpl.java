package com.cby.tcs.uav_harvest_schedule.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.uav_harvest_schedule.dao.UavHarvestScheduleDao;
import com.cby.tcs.uav_harvest_schedule.entity.po.UavHarvestSchedule;
import com.cby.tcs.uav_harvest_schedule.service.UavHarvestScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UavHarvestScheduleServiceImpl extends ServiceImpl<UavHarvestScheduleDao, UavHarvestSchedule> implements UavHarvestScheduleService {

  private final UavHarvestScheduleDao uavHarvestScheduleDao;
}
