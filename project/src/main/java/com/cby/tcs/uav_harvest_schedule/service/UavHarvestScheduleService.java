package com.cby.tcs.uav_harvest_schedule.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cby.tcs.uav_harvest_schedule.entity.fo.GetHsIdUavHarvestScheduleFo;
import com.cby.tcs.uav_harvest_schedule.entity.po.UavHarvestSchedule;

public interface UavHarvestScheduleService extends IService<UavHarvestSchedule> {

    String getDispatchId(GetHsIdUavHarvestScheduleFo entity);
}
