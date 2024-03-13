package com.cby.tcs.farm_cotton_picker.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.common.entity.BasicOption;
import com.cby.tcs.exception.FarmCottonPickerException;
import com.cby.tcs.farm_cotton_picker.dao.FarmCottonPickerDao;
import com.cby.tcs.farm_cotton_picker.entity.fo.CottonPickerFo;
import com.cby.tcs.farm_cotton_picker.entity.fo.CottonPickerPageFo;
import com.cby.tcs.farm_cotton_picker.entity.po.FarmCottonPicker;
import com.cby.tcs.farm_cotton_picker.entity.vo.CottonPickerVo;
import com.cby.tcs.farm_cotton_picker.service.FarmCottonPickerService;
import com.cby.tcs.farm_terminal_picker.entity.po.FarmTerminalPicker;
import com.cby.tcs.farm_terminal_picker.service.FarmTerminalPickerService;
import com.freedom.cloud.utils.page.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 农机管理-采棉机 服务实现类
 * </p>
 */
@Service
@RequiredArgsConstructor
public class FarmCottonPickerServiceImpl extends ServiceImpl<FarmCottonPickerDao, FarmCottonPicker> implements FarmCottonPickerService {

  private final FarmCottonPickerDao farmCottonPickerDao;

  private final FarmTerminalPickerService farmTerminalPickerService;

  @Override
  public List<BasicOption> getBrandOptions() {
    List<String> brands = farmCottonPickerDao.getBrandOptions();
    List<BasicOption> options = new ArrayList<>();
    for (String brand : brands) {
      options.add(new BasicOption().setLabel(brand).setValue(brand));
    }
    return options;
  }

  @Override
  public Page<CottonPickerVo> getFilterPage(CottonPickerPageFo entity) {
    return farmCottonPickerDao.getFilterPage(PageUtils.getPage(entity), entity);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void addFarmCottonPicker(CottonPickerFo entity) {
    LambdaQueryWrapper<FarmCottonPicker> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(FarmCottonPicker::getRackNumber, entity.getRackNumber());
    FarmCottonPicker farmCottonPicker = getOne(wrapper);
    if (Objects.nonNull(farmCottonPicker)) throw new FarmCottonPickerException("农机编号【%s】已存在，不可重复添加");
    farmCottonPicker = BeanUtil.copyProperties(entity, FarmCottonPicker.class);
    save(farmCottonPicker);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delFarmCottonPicker(String id) {
    if (StrUtil.hasBlank(id)) throw new FarmCottonPickerException("不可传递空值");
    removeById(id);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateFarmCottonPicker(CottonPickerFo entity) {
    if (StrUtil.hasBlank(entity.getId())) throw new FarmCottonPickerException("请传递采棉机记录Id编号");
    FarmCottonPicker farmCottonPicker = getById(entity.getId());
    if (Objects.isNull(farmCottonPicker)) throw new FarmCottonPickerException("未找到该采棉机入库信息");
    if (!entity.getRackNumber().equals(farmCottonPicker.getRackNumber())) {
      LambdaQueryWrapper<FarmTerminalPicker> wrapper = new LambdaQueryWrapper<>();
      wrapper.eq(FarmTerminalPicker::getCottonPickerRackId, farmCottonPicker.getRackNumber());
      List<FarmTerminalPicker> terminalPickers = farmTerminalPickerService.list(wrapper);
      terminalPickers.forEach(item -> item.setCottonPickerRackId(entity.getRackNumber()));
      farmTerminalPickerService.updateBatchById(terminalPickers);
    }
    farmCottonPicker = BeanUtil.copyProperties(entity, FarmCottonPicker.class);
    updateById(farmCottonPicker);
  }

}
