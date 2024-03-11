package com.cby.tcs.harvest_schedule.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.cotton_field.entity.po.CottonField;
import com.cby.tcs.cotton_field.entity.vo.CottonFieldVo;
import com.cby.tcs.cotton_field.service.CottonFieldService;
import com.cby.tcs.ginnery.entity.po.Ginnery;
import com.cby.tcs.ginnery.entity.vo.GinneryBasicVo;
import com.cby.tcs.ginnery.service.GinneryService;
import com.cby.tcs.harvest_schedule.dao.HarvestScheduleDao;
import com.cby.tcs.harvest_schedule.entity.fo.AddHarvestScheduleFo;
import com.cby.tcs.harvest_schedule.entity.fo.FilterPageFo;
import com.cby.tcs.harvest_schedule.entity.po.HarvestSchedule;
import com.cby.tcs.harvest_schedule.entity.vo.HarvestScheduleDetailsVo;
import com.cby.tcs.harvest_schedule.entity.vo.HarvestScheduleVo;
import com.cby.tcs.harvest_schedule.service.HarvestScheduleService;
import com.cby.tcs.user.entity.vo.UserInfo;
import com.cby.tcs.user.service.UserService;
import com.cby.tcs.utils.RedisUtils;
import com.freedom.cloud.enums.LogicalEnum;
import com.freedom.cloud.utils.page.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HarvestScheduleServiceImpl extends ServiceImpl<HarvestScheduleDao, HarvestSchedule> implements HarvestScheduleService {

  private final HarvestScheduleDao harvestScheduleDao;

  private final RedisUtils redisUtils;

  private final GinneryService ginneryService;

  private final CottonFieldService cottonFieldService;

  private final UserService userService;

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
  public String createDispatchId() {
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

  @Override
  public HarvestScheduleDetailsVo getDetails(String dispatchId) {
    LambdaQueryWrapper<HarvestSchedule> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(HarvestSchedule::getDispatchId, dispatchId);
    HarvestSchedule harvestSchedule = getOne(wrapper);
    Ginnery ginnery = ginneryService.getById(harvestSchedule.getGinneryId());
    HarvestScheduleDetailsVo detailsVo = BeanUtil.copyProperties(ginnery, HarvestScheduleDetailsVo.class, "contacts");
    detailsVo.setDispatchId(harvestSchedule.getDispatchId())
            .setCreateTime(harvestSchedule.getCreateTime());
    List<String> cottonIds = Arrays.stream(harvestSchedule.getCottonFieldId().split(",")).toList();
    List<CottonField> cottonFields = cottonFieldService.listByIds(cottonIds);
    List<String> userIds = new ArrayList<>(cottonFields.stream()
            .map(CottonField::getContacts)
            .filter(StrUtil::hasBlank)
            .distinct()
            .toList());
    if (!StrUtil.hasBlank(ginnery.getContacts())) userIds.add(ginnery.getContacts());
    Map<String, UserInfo> userInfoMap = userService.getUserInfoList(userIds)
            .stream()
            .collect(Collectors.toMap(UserInfo::getId, Function.identity()));
    detailsVo.setContacts(userInfoMap.get(ginnery.getContacts()));
    detailsVo.setChildren(new ArrayList<>());
    detailsVo.setDispatchArea(0D);
    for (CottonField cottonField : cottonFields) {
      CottonFieldVo fieldVo = BeanUtil.copyProperties(cottonField, CottonFieldVo.class, "ginnery", "contacts");
      GinneryBasicVo ginneryContacts = BeanUtil.copyProperties(ginnery, GinneryBasicVo.class, "contacts");
      ginneryContacts.setContacts(userInfoMap.get(ginnery.getContacts()));
      fieldVo.setGinnery(ginneryContacts).setContacts(userInfoMap.get(cottonField.getContacts()));
      detailsVo.getChildren().add(fieldVo);
      detailsVo.setDispatchArea(detailsVo.getDispatchArea() + cottonField.getCultivatedArea());
    }
    detailsVo.setId(harvestSchedule.getId());
    return detailsVo;
  }

}
