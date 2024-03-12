package com.cby.tcs.farm_cotton_picker.service.impl;

import com.cby.tcs.farm_cotton_picker.dao.FarmCottonPickerDao;
import com.cby.tcs.farm_cotton_picker.service.FarmCottonPickerService;
import com.cby.tcs.farm_cotton_picker.entity.po.FarmCottonPicker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 农机管理-采棉机 服务实现类
 * </p>
 */
@Service
@RequiredArgsConstructor
public class FarmCottonPickerServiceImpl extends ServiceImpl<FarmCottonPickerDao, FarmCottonPicker> implements FarmCottonPickerService {

  private final FarmCottonPickerDao farmCottonPickerDao;
}
