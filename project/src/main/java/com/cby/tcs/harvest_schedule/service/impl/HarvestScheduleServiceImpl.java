package com.cby.tcs.harvest_schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cby.tcs.harvest_schedule.dao.HarvestScheduleDao;
import com.cby.tcs.harvest_schedule.entity.fo.AddHarvestScheduleFo;
import com.cby.tcs.harvest_schedule.entity.po.HarvestSchedule;
import com.cby.tcs.harvest_schedule.service.HarvestScheduleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freedom.cloud.enums.LogicalEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class HarvestScheduleServiceImpl extends ServiceImpl<HarvestScheduleDao, HarvestSchedule> implements HarvestScheduleService {

  private final HarvestScheduleDao harvestScheduleDao;

  @Override
  public void add(AddHarvestScheduleFo entity) {
    HarvestSchedule existingSchedule = harvestScheduleDao.selectOne(new LambdaQueryWrapper<HarvestSchedule>()
            .eq(HarvestSchedule::getDispatchId, entity.getDispatchId()));

    String cottonFieldIds = String.join(",", entity.getCottonFieldIds());

    if (existingSchedule == null) {
      int randomNumber = new Random().nextInt(100);
      HarvestSchedule newHarvestSchedule = new HarvestSchedule()
              .setDispatchId(String.valueOf(randomNumber))
              .setGinneryId(entity.getGinneryId())
              .setCottonFieldId(cottonFieldIds)
              .setStatus(LogicalEnum.NO)
              .setDeleted(0);
      harvestScheduleDao.insert(newHarvestSchedule);
    } else {
      String updatedCottonFieldIds = existingSchedule.getCottonFieldId() + "," + cottonFieldIds;
      existingSchedule.setCottonFieldId(updatedCottonFieldIds);
      harvestScheduleDao.updateById(existingSchedule);
    }
  }
}
