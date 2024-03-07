package com.cby.tcs.ginnery.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.cotton_field.entity.po.CottonField;
import com.cby.tcs.cotton_field.entity.vo.CottonFieldVo;
import com.cby.tcs.cotton_field.service.CottonFieldService;
import com.cby.tcs.ginnery.dao.GinneryDao;
import com.cby.tcs.ginnery.entity.fo.GinneryPageFo;
import com.cby.tcs.ginnery.entity.po.Ginnery;
import com.cby.tcs.ginnery.entity.vo.GinneryBasicVo;
import com.cby.tcs.ginnery.entity.vo.GinneryVo;
import com.cby.tcs.ginnery.service.GinneryService;
import com.cby.tcs.user.entity.vo.UserInfo;
import com.cby.tcs.user.service.UserService;
import com.freedom.cloud.utils.page.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GinneryServiceImpl extends ServiceImpl<GinneryDao, Ginnery> implements GinneryService {

  private final GinneryDao ginneryDao;

  private final CottonFieldService cottonFieldService;

  private final UserService userService;

  @Override
  public Page<GinneryVo> search(GinneryPageFo entity) {
    Page<GinneryVo> voPage = new Page<>();
    ArrayList<GinneryVo> ginneryVos = new ArrayList<>();
    LambdaQueryWrapper<Ginnery> wrapper = new LambdaQueryWrapper<>();
    wrapper.like(Ginnery::getFactoryName, entity.getFactoryName());
    Page<Ginnery> ginneries = page(PageUtils.getPage(entity), wrapper);
    List<Ginnery> records = ginneries.getRecords();
    if (records.isEmpty()) return voPage;
    // 类型变换
    Map<String, GinneryBasicVo> ginneryMap = records
            .stream()
            .map(item -> BeanUtil.copyProperties(item, GinneryBasicVo.class, "contacts"))
            .collect(Collectors.toMap(GinneryBasicVo::getId, Function.identity()));
    List<String> factoryIds = records.stream().map(Ginnery::getId).toList();
    // 查询所有地块
    LambdaQueryWrapper<CottonField> cottonWrapper = new LambdaQueryWrapper<>();
    cottonWrapper.in(CottonField::getGinneryId, factoryIds);
    List<CottonField> cottonFields = cottonFieldService.list(cottonWrapper);
    // 归集所有联系人，统一查询
    List<String> contactsIds = new ArrayList<>(cottonFields.stream().map(CottonField::getContacts).toList());
    contactsIds.addAll(records.stream().map(Ginnery::getContacts).toList());
    Map<String, UserInfo> userInfoMap = userService.getUserInfoList(contactsIds)
            .stream()
            .collect(Collectors.toMap(UserInfo::getId, Function.identity()));
    // 设置返回体
    for (Ginnery ginnery : records) {
      GinneryVo ginneryVo = BeanUtil.copyProperties(ginnery, GinneryVo.class, "children", "contacts");
      ginneryVo.setContacts(userInfoMap.get(ginnery.getId()));
      if (Objects.isNull(ginneryVo.getChildren())) {
        ginneryVo.setChildren(new ArrayList<>());
      }
      for (CottonField field : cottonFields) {
        if (field.getGinneryId().equals(ginnery.getId())) {
          CottonFieldVo fieldVo = BeanUtil.copyProperties(field, CottonFieldVo.class, "ginnery", "contacts");
          fieldVo.setGinnery(ginneryMap.get(ginnery.getId()))
                  .setContacts(userInfoMap.get(field.getContacts()));
          ginneryVo.getChildren().add(fieldVo);
        }
      }
      ginneryVos.add(ginneryVo);
    }
    BeanUtil.copyProperties(ginneries, voPage, "records");
    return voPage.setRecords(ginneryVos);
  }
}
