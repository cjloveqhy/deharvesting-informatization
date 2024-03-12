package com.cby.tcs.cotton_field.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.cotton_field.dao.CottonFieldDao;
import com.cby.tcs.cotton_field.entity.fo.CottonFieldByIdsFo;
import com.cby.tcs.cotton_field.entity.po.CottonField;
import com.cby.tcs.cotton_field.entity.vo.CottonFieldVo;
import com.cby.tcs.cotton_field.service.CottonFieldService;
import com.cby.tcs.ginnery.dao.GinneryDao;
import com.cby.tcs.ginnery.entity.po.Ginnery;
import com.cby.tcs.ginnery.entity.vo.GinneryBasicVo;
import com.cby.tcs.user.entity.vo.UserInfo;
import com.cby.tcs.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CottonFieldServiceImpl extends ServiceImpl<CottonFieldDao, CottonField> implements CottonFieldService {

  private final CottonFieldDao cottonFieldDao;

  private final UserService userService;

  private final GinneryDao ginneryDao;

  @Override
  public List<CottonFieldVo> getCottons(String factoryId) {
    List<CottonFieldVo> cottonFieldVos = new ArrayList<>();
    LambdaQueryWrapper<CottonField> cottonWrapper = new LambdaQueryWrapper<>();
    cottonWrapper.eq(CottonField::getGinneryId, factoryId);
    List<CottonField> cottonFields = list(cottonWrapper);
    if (cottonFields.isEmpty()) return cottonFieldVos;
    // 归集所有联系人，统一查询
    List<String> contactsIds = new ArrayList<>(cottonFields.stream().map(CottonField::getContacts).toList());
    contactsIds.addAll(cottonFields.stream().map(CottonField::getContacts).toList());
    Ginnery ginnery = ginneryDao.selectById(factoryId);
    GinneryBasicVo ginneryBasicVo = BeanUtil.copyProperties(ginnery, GinneryBasicVo.class, "contacts");
    if (!StrUtil.hasBlank(ginnery.getContacts())) contactsIds.add(ginnery.getContacts());
    Map<String, UserInfo> userInfoMap = userService.getUserInfoList(contactsIds)
            .stream()
            .collect(Collectors.toMap(UserInfo::getId, Function.identity()));
    ginneryBasicVo.setContacts(userInfoMap.get(ginnery.getContacts()));
    for (CottonField cottonField : cottonFields) {
      CottonFieldVo fieldVo = BeanUtil.copyProperties(cottonField, CottonFieldVo.class, "ginnery", "contacts");
      fieldVo.setContacts(userInfoMap.get(cottonField.getContacts()))
              .setGinnery(ginneryBasicVo);
      cottonFieldVos.add(fieldVo);
    }
    return cottonFieldVos;
  }

  @Override
  public List<CottonFieldVo> getCottonsByIds(CottonFieldByIdsFo cottonFieldByIdsFo) {
    List<CottonFieldVo> cottonFieldVos = new ArrayList<>();
    LambdaQueryWrapper<CottonField> cottonWrapper = new LambdaQueryWrapper<>();
    cottonWrapper.in(CottonField::getId, cottonFieldByIdsFo.getCottonFieldIds());
    List<CottonField> cottonFields = list(cottonWrapper);
    if (cottonFields.isEmpty()) return cottonFieldVos;
    // 归集所有联系人，统一查询
    List<String> contactsIds = new ArrayList<>(cottonFields.stream().map(CottonField::getContacts).toList());
    contactsIds.addAll(cottonFields.stream().map(CottonField::getContacts).toList());
    Ginnery ginnery = ginneryDao.selectById(cottonFieldByIdsFo.getGinneryId());
    GinneryBasicVo ginneryBasicVo = BeanUtil.copyProperties(ginnery, GinneryBasicVo.class, "contacts");
    if (!StrUtil.hasBlank(ginnery.getContacts())) contactsIds.add(ginnery.getContacts());
    Map<String, UserInfo> userInfoMap = userService.getUserInfoList(contactsIds)
            .stream()
            .collect(Collectors.toMap(UserInfo::getId, Function.identity()));
    ginneryBasicVo.setContacts(userInfoMap.get(ginnery.getContacts()));
    for (CottonField cottonField : cottonFields) {
      CottonFieldVo fieldVo = BeanUtil.copyProperties(cottonField, CottonFieldVo.class, "ginnery", "contacts");
      fieldVo.setContacts(userInfoMap.get(cottonField.getContacts()))
              .setGinnery(ginneryBasicVo);
      cottonFieldVos.add(fieldVo);
    }
    return cottonFieldVos;
  }
}
