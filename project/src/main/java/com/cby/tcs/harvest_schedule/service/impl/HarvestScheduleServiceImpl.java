package com.cby.tcs.harvest_schedule.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.cotton_field.dao.CottonFieldDao;
import com.cby.tcs.cotton_field.entity.fo.CottonFieldByIdsFo;
import com.cby.tcs.cotton_field.entity.po.CottonField;
import com.cby.tcs.cotton_field.entity.vo.CottonFieldVo;
import com.cby.tcs.cotton_field.service.CottonFieldService;
import com.cby.tcs.exception.HarvestScheduleException;
import com.cby.tcs.farm_uav.dao.FarmUavDao;
import com.cby.tcs.ginnery.dao.GinneryDao;
import com.cby.tcs.ginnery.entity.fo.GinneryPageFo;
import com.cby.tcs.ginnery.entity.po.Ginnery;
import com.cby.tcs.ginnery.entity.vo.GinneryBasicVo;
import com.cby.tcs.ginnery.entity.vo.GinneryVo;
import com.cby.tcs.ginnery.service.GinneryService;
import com.cby.tcs.harvest_schedule.dao.HarvestScheduleDao;
import com.cby.tcs.harvest_schedule.entity.fo.*;
import com.cby.tcs.harvest_schedule.entity.po.HarvestSchedule;
import com.cby.tcs.harvest_schedule.entity.vo.HarvestScheduleDetailsVo;
import com.cby.tcs.harvest_schedule.entity.vo.HarvestScheduleRecordVo;
import com.cby.tcs.harvest_schedule.entity.vo.HarvestScheduleVo;
import com.cby.tcs.harvest_schedule.service.HarvestScheduleService;
import com.cby.tcs.uav_harvest_schedule.dao.UavHarvestScheduleDao;
import com.cby.tcs.uav_harvest_schedule.entity.po.UavHarvestSchedule;
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

  private final CottonFieldDao cottonFieldDao;

  private final GinneryDao ginneryDao;

  private final FarmUavDao farmUavDao;

  private final UavHarvestScheduleDao uavHarvestScheduleDao;

  private final GinneryService ginneryService;

  private final RedisUtils redisUtils;

  private final CottonFieldService cottonFieldService;

  private final UserService userService;

  private static volatile String dispatchIdKey = "dispatch:key";

  @Override
  public void add(AddHarvestScheduleFo entity) {
    HarvestSchedule existingSchedule = harvestScheduleDao.selectOne(new LambdaQueryWrapper<HarvestSchedule>()
            .eq(HarvestSchedule::getGinneryId, entity.getGinneryId()).eq(HarvestSchedule::getStatus, LogicalEnum.NO));

    String cottonFieldId = entity.getCottonFieldId();
    if (existingSchedule == null) {
      String dispatchId = createDispatchId();
      HarvestSchedule newHarvestSchedule = new HarvestSchedule()
              .setDispatchId(dispatchId)
              .setGinneryId(entity.getGinneryId())
              .setCottonFieldId(cottonFieldId)
              .setStatus(LogicalEnum.NO)
              .setDeleted(0);
      harvestScheduleDao.insert(newHarvestSchedule);
    } else {
      if (!existingSchedule.getCottonFieldId().isEmpty()){
        List<String> cottonFieldList = Arrays.stream(existingSchedule.getCottonFieldId().split(",")).toList();
        if (!cottonFieldList.contains(entity.getCottonFieldId())){
          cottonFieldId = String.join(",", existingSchedule.getCottonFieldId(), entity.getCottonFieldId());
        } else {
          cottonFieldId = existingSchedule.getCottonFieldId();
        }
      }
      existingSchedule.setCottonFieldId(cottonFieldId);
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
  public HarvestScheduleRecordVo getRecord(HarvestScheduleRecordPageFo entity) {
    HarvestSchedule harvestSchedule = harvestScheduleDao.selectOne(new LambdaQueryWrapper<HarvestSchedule>()
            .eq(HarvestSchedule::getGinneryId, entity.getGinneryId()).eq(HarvestSchedule::getStatus, LogicalEnum.NO));
    // 轧花厂信息
    Ginnery ginnery = ginneryDao.selectById(harvestSchedule.getGinneryId());
    GinneryPageFo ginneryPageFo = new GinneryPageFo();
    ginneryPageFo.setFactoryName(ginnery.getFactoryName()).setPage(entity.getPage()).setSize(entity.getSize());
    Page<GinneryVo> ginneryVoPage = ginneryService.search(ginneryPageFo);
    // 棉地信息
    CottonFieldByIdsFo cottonFieldByIdsFo = new CottonFieldByIdsFo();
    cottonFieldByIdsFo.setGinneryId(harvestSchedule.getGinneryId())
            .setCottonFieldIds(Arrays.stream(harvestSchedule.getCottonFieldId().split(",")).toList());
    // 设置返回体
    HarvestScheduleRecordVo harvestScheduleRecordVo = new HarvestScheduleRecordVo();
    harvestScheduleRecordVo.setDispatchId(harvestSchedule.getDispatchId()).setGinnery(ginneryVoPage.getRecords().get(0))
            .setCottonFields(cottonFieldService.getCottonsByIds(cottonFieldByIdsFo))
            .setCreateTime(harvestSchedule.getCreateTime());
    return harvestScheduleRecordVo;
  }

  @Override
  public void deleteHarvestScheduleRecordCottonField(DeleteHarvestScheduleRecordCottonFieldFo entity) {
    HarvestSchedule harvestSchedule = harvestScheduleDao
            .selectOne(new LambdaQueryWrapper<HarvestSchedule>().eq(HarvestSchedule::getDispatchId, entity.getDispatchId()));
    List<String> cottonFields = new ArrayList<>(Arrays.asList(harvestSchedule.getCottonFieldId().split(",")));
    for (int i = 0; i < cottonFields.size(); i++){
      if (entity.getCottonFieldId().equals(cottonFields.get(i))){
        cottonFields.remove(i);
      }
    }
    String updateCottonField = String.join(",", cottonFields);
    harvestSchedule.setCottonFieldId(updateCottonField);
    harvestScheduleDao.updateById(harvestSchedule);
  }

  @Override
  public void create(CreateHarvestScheduleFo createHarvestScheduleFo) {
    HarvestSchedule harvestSchedule = harvestScheduleDao
            .selectOne(new LambdaQueryWrapper<HarvestSchedule>()
                    .eq(HarvestSchedule::getDispatchId, createHarvestScheduleFo.getDispatchId()));
    harvestSchedule.setStatus(LogicalEnum.YES).setCreator(String.valueOf(StpUtil.getLoginId()));
    List<String> uavBelongerIds = farmUavDao.uavBelongerIds();
    UavHarvestSchedule uavHarvestSchedule = new UavHarvestSchedule();
    Random random = new Random();
    uavHarvestSchedule.setHsId(createHarvestScheduleFo.getDispatchId())
            .setBelonger(uavBelongerIds.get(random.nextInt(uavBelongerIds.size())));
    List<String> cottonFieldIdList = Arrays.asList(harvestSchedule.getCottonFieldId().split(","));
    uavHarvestSchedule.setCottonFieldId(String.valueOf(cottonFieldIdList.get(random.nextInt(cottonFieldIdList.size()))));
    uavHarvestScheduleDao.insert(uavHarvestSchedule);
    harvestScheduleDao.updateById(harvestSchedule);
  }

  @Override
  public HarvestScheduleDetailsVo getDetails(String dispatchId) {
    LambdaQueryWrapper<HarvestSchedule> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(HarvestSchedule::getDispatchId, dispatchId)
            .eq(HarvestSchedule::getStatus, LogicalEnum.YES);
    HarvestSchedule harvestSchedule = getOne(wrapper);
    if (Objects.isNull(harvestSchedule)) throw new HarvestScheduleException("调度单号为: %s的调度信息不存在", dispatchId);
    Ginnery ginnery = ginneryService.getById(harvestSchedule.getGinneryId());
    HarvestScheduleDetailsVo detailsVo = BeanUtil.copyProperties(ginnery, HarvestScheduleDetailsVo.class, "contacts");
    detailsVo.setDispatchId(harvestSchedule.getDispatchId())
            .setCreateTime(harvestSchedule.getCreateTime());
    List<String> cottonIds = Arrays.stream(harvestSchedule.getCottonFieldId().split(",")).toList();
    List<CottonField> cottonFields = cottonFieldService.listByIds(cottonIds);
    List<String> userIds = new ArrayList<>(cottonFields.stream()
            .map(CottonField::getContacts)
            .filter(StrUtil::isNotBlank)
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
  @Override
  public Page<HarvestScheduleVo> checkOrder(FilterPageFo entity) {
    // 获取用户的拥有的调度单id
    List<String> hsIdList = uavHarvestScheduleDao.selectList(new LambdaQueryWrapper<UavHarvestSchedule>()
            .eq(UavHarvestSchedule::getBelonger, StpUtil.getLoginId())).stream().map(UavHarvestSchedule::getHsId).toList();
    if (Objects.nonNull(entity.getGinneryName()) && entity.getGinneryName().startsWith("DD")) {
      entity.setDispatchId(entity.getGinneryName());
      entity.setGinneryName(null);
    }
    entity.setSize(harvestScheduleDao.selectList(null).size());
    // 获取所有的调度单信息
    Page<HarvestScheduleVo> harvestScheduleVoPage = harvestScheduleDao.filterPage(PageUtils.getPage(entity), entity);
    List<HarvestScheduleVo> filteredList = harvestScheduleVoPage.getRecords().stream()
            .filter(item -> hsIdList.contains(item.getDispatchId()))
            .collect(Collectors.toList());
    // 创建一个新的Page对象，将过滤后的数据放入其中
    Page<HarvestScheduleVo> filterPage = new Page<>(1, filteredList.size());
    filterPage.setRecords(filteredList);
    return filterPage;
  }
}
