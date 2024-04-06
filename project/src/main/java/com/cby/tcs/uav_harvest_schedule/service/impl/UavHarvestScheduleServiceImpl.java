package com.cby.tcs.uav_harvest_schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.uav_harvest_schedule.dao.UavHarvestScheduleDao;
import com.cby.tcs.uav_harvest_schedule.entity.fo.GetHsIdUavHarvestScheduleFo;
import com.cby.tcs.uav_harvest_schedule.entity.po.UavHarvestSchedule;
import com.cby.tcs.uav_harvest_schedule.service.UavHarvestScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UavHarvestScheduleServiceImpl extends ServiceImpl<UavHarvestScheduleDao, UavHarvestSchedule> implements UavHarvestScheduleService {

  private final UavHarvestScheduleDao uavHarvestScheduleDao;

  @Override
  public String getDispatchId(GetHsIdUavHarvestScheduleFo entity) {
    LambdaQueryWrapper<UavHarvestSchedule> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(UavHarvestSchedule::getBelonger, entity.getBelonger()).eq(UavHarvestSchedule::getCottonFieldId, entity.getCottonFieldId());
    UavHarvestSchedule uavHarvestSchedule = uavHarvestScheduleDao.selectOne(wrapper);
    if (Objects.isNull(uavHarvestSchedule)) return null;
    String dispatchId = uavHarvestSchedule.getHsId();
    return dispatchId;
  }
}
