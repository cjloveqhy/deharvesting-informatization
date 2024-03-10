package com.cby.tcs.harvest_schedule.service;

import com.cby.tcs.harvest_schedule.entity.fo.AddHarvestScheduleFo;
import com.cby.tcs.harvest_schedule.entity.po.HarvestSchedule;
import com.baomidou.mybatisplus.extension.service.IService;

public interface HarvestScheduleService extends IService<HarvestSchedule> {

    void add(AddHarvestScheduleFo entity);
}
