package com.cby.tcs.harvest_schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.harvest_schedule.dao.HarvestScheduleDao;
import com.cby.tcs.harvest_schedule.entity.fo.AddHarvestScheduleFo;
import com.cby.tcs.harvest_schedule.entity.fo.FilterPageFo;
import com.cby.tcs.harvest_schedule.entity.po.HarvestSchedule;
import com.cby.tcs.harvest_schedule.entity.vo.HarvestScheduleVo;
import com.cby.tcs.harvest_schedule.service.HarvestScheduleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.utils.RedisUtils;
import com.freedom.cloud.enums.LogicalEnum;
import com.freedom.cloud.utils.page.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class HarvestScheduleServiceImpl extends ServiceImpl<HarvestScheduleDao, HarvestSchedule> implements HarvestScheduleService {

  private final HarvestScheduleDao harvestScheduleDao;

  private final RedisUtils redisUtils;

  private static volatile String dispatchIdKey = "dispatch:key";

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




  @Override
  public Page<HarvestScheduleVo> filterPage(FilterPageFo entity) {
    if (Objects.nonNull(entity.getGinneryName()) && entity.getGinneryName().startsWith("DD")) {
      entity.setDispatchId(entity.getGinneryName());
      entity.setGinneryName(null);
    }
    return harvestScheduleDao.filterPage(PageUtils.getPage(entity), entity);
  }

  @Override
  public String createId() {
    Integer value = redisUtils.get(dispatchIdKey, Integer.class);
    if (value == null) {
      synchronized (this) {
        if (value == null) {
          value = 0;
        }
      }
    }
    value++;
    redisUtils.set(dispatchIdKey, value);
    StringBuffer dispatchId = new StringBuffer("DD");
    Calendar calendar = Calendar.getInstance();
    dispatchId.append(calendar.get(Calendar.YEAR))
            .append(String.format("%02d", calendar.get(Calendar.MONTH) + 1))
            .append(String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)));
    Integer keyValue = redisUtils.get(dispatchIdKey, Integer.class);
    dispatchId.append(String.format("%03d", keyValue));
    return dispatchId.toString();
  }

}
