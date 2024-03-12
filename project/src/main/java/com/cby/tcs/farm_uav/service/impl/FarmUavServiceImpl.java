package com.cby.tcs.farm_uav.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.farm_uav.dao.FarmUavDao;
import com.cby.tcs.farm_uav.entity.fo.FarmUavPageFo;
import com.cby.tcs.farm_uav.entity.po.FarmUav;
import com.cby.tcs.farm_uav.entity.vo.FarmUavVo;
import com.cby.tcs.farm_uav.service.FarmUavService;
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

/**
 * <p>
 * 农机管理-无人机 服务实现类
 * </p>
 */
@Service
@RequiredArgsConstructor
public class FarmUavServiceImpl extends ServiceImpl<FarmUavDao, FarmUav> implements FarmUavService {

  private final FarmUavDao farmUavDao;

  private final UserService userService;

  @Override
  public Page<FarmUavVo> getFilterPage(FarmUavPageFo entity) {
    LambdaQueryWrapper<FarmUav> wrapper = new LambdaQueryWrapper<>();
    if (!StrUtil.hasBlank(entity.getRackNumber())) wrapper.eq(FarmUav::getRackNumber, entity.getRackNumber());
    if (!StrUtil.hasBlank(entity.getServiceProvider())) wrapper.eq(FarmUav::getServiceProvider, entity.getServiceProvider());
    if (Objects.nonNull(entity.getHoldingAttr())) wrapper.eq(FarmUav::getHoldingAttr, entity.getHoldingAttr());
    Page<FarmUav> page = page(PageUtils.getPage(entity), wrapper);
    List<String> userIds = page.getRecords().stream().map(FarmUav::getBelonger).filter(StrUtil::isNotBlank).distinct().toList();
    Map<String, UserInfo> userInfoMap = userService.getUserInfoList(userIds)
            .stream()
            .collect(Collectors.toMap(UserInfo::getId, Function.identity()));
    Page<FarmUavVo> voPage = new Page<>();
    BeanUtil.copyProperties(page, voPage.getRecords(), "records");
    List<FarmUavVo> farmUavVos = new ArrayList<>();
    for (FarmUav record : page.getRecords()) {
      FarmUavVo uavVo = BeanUtil.copyProperties(record, FarmUavVo.class, "belonger");
      uavVo.setBelonger(userInfoMap.get(record.getBelonger()));
      farmUavVos.add(uavVo);
    }
    return voPage.setRecords(farmUavVos);
  }
}
