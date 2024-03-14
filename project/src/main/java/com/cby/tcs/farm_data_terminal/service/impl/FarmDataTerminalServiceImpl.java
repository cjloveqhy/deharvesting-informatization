package com.cby.tcs.farm_data_terminal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.common.entity.BasicOption;
import com.cby.tcs.exception.FarmDataTerminalException;
import com.cby.tcs.farm_data_terminal.dao.FarmDataTerminalDao;
import com.cby.tcs.farm_data_terminal.entity.fo.DataTerminalFo;
import com.cby.tcs.farm_data_terminal.entity.fo.DataTerminalPageFo;
import com.cby.tcs.farm_data_terminal.entity.po.FarmDataTerminal;
import com.cby.tcs.farm_data_terminal.entity.vo.DataTerminalVo;
import com.cby.tcs.farm_data_terminal.service.FarmDataTerminalService;
import com.cby.tcs.farm_terminal_picker.entity.po.FarmTerminalPicker;
import com.cby.tcs.farm_terminal_picker.service.FarmTerminalPickerService;
import com.freedom.cloud.enums.LogicalEnum;
import com.freedom.cloud.utils.page.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 农机管理-数据终端 服务实现类
 * </p>
 */
@Service
@RequiredArgsConstructor
public class FarmDataTerminalServiceImpl extends ServiceImpl<FarmDataTerminalDao, FarmDataTerminal> implements FarmDataTerminalService {

  private final FarmDataTerminalDao farmDataTerminalDao;

  private final FarmTerminalPickerService farmTerminalPickerService;

  @Override
  public List<BasicOption> getBrandOptions() {
    List<String> brands = farmDataTerminalDao.getBrandOptions();
    List<BasicOption> options = new ArrayList<>();
    for (String brand : brands) {
      options.add(new BasicOption().setLabel(brand).setValue(brand));
    }
    return options;
  }

  @Override
  public Page<DataTerminalVo> getFilterPage(DataTerminalPageFo entity) {
    return farmDataTerminalDao.getFilterPage(PageUtils.getPage(entity), entity);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void addFarmDataTerminal(DataTerminalFo entity) {
    if (entity.getStatus().equals(LogicalEnum.YES) && StrUtil.hasBlank(entity.getCottonPickerRackId()))
      throw new FarmDataTerminalException("请填写关联的农机编号");
    LambdaQueryWrapper<FarmDataTerminal> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(FarmDataTerminal::getTerminalId, entity.getTerminalId());
    FarmDataTerminal farmDataTerminal = getOne(wrapper);
    if (Objects.nonNull(farmDataTerminal))
      throw new FarmDataTerminalException("终端编号为【%s】的记录已存在，不可重复添加", farmDataTerminal.getTerminalId());
    farmDataTerminal = BeanUtil.copyProperties(entity, FarmDataTerminal.class);
    FarmTerminalPicker farmTerminalPicker = new FarmTerminalPicker()
            .setStatus(entity.getStatus())
            .setCottonPickerRackId(entity.getCottonPickerRackId())
            .setDataTerminalRackId(farmDataTerminal.getTerminalId());
    save(farmDataTerminal);
    if (entity.getStatus().equals(LogicalEnum.YES)) {
      farmTerminalPickerService.save(farmTerminalPicker);
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateFarmDataTerminal(DataTerminalFo entity) {
    if (StrUtil.hasBlank(entity.getId())) throw new FarmDataTerminalException("请传递数据终端记录Id编号");
    FarmDataTerminal farmDataTerminal = getById(entity.getId());
    LambdaQueryWrapper<FarmTerminalPicker> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(FarmTerminalPicker::getDataTerminalRackId, farmDataTerminal.getTerminalId());
    FarmTerminalPicker terminalPicker = farmTerminalPickerService.getOne(wrapper);
    if (Objects.nonNull(terminalPicker)) {
      terminalPicker.setCottonPickerRackId(entity.getCottonPickerRackId())
              .setDataTerminalRackId(entity.getTerminalId())
              .setStatus(entity.getStatus());
      farmDataTerminal = BeanUtil.copyProperties(entity, FarmDataTerminal.class);
      farmTerminalPickerService.updateById(terminalPicker);
    } else {
      if (entity.getStatus().equals(LogicalEnum.YES)) {
        terminalPicker = new FarmTerminalPicker()
                .setStatus(entity.getStatus())
                .setDataTerminalRackId(entity.getTerminalId())
                .setCottonPickerRackId(entity.getCottonPickerRackId());
        farmTerminalPickerService.save(terminalPicker);
      }
    }
    updateById(farmDataTerminal);

  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delFarmDataTerminal(String id) {
    if (StrUtil.hasBlank(id)) throw new FarmDataTerminalException("不可传递空值");
    FarmDataTerminal farmDataTerminal = getById(id);
    if (Objects.isNull(farmDataTerminal)) throw new FarmDataTerminalException("删除失败，该记录不存在");
    LambdaQueryWrapper<FarmTerminalPicker> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(FarmTerminalPicker::getDataTerminalRackId, farmDataTerminal.getTerminalId());
    farmTerminalPickerService.remove(wrapper);
    removeById(id);
  }

}
