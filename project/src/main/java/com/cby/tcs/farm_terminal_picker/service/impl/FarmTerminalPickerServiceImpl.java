package com.cby.tcs.farm_terminal_picker.service.impl;

import com.cby.tcs.farm_terminal_picker.entity.po.FarmTerminalPicker;
import com.cby.tcs.farm_terminal_picker.dao.FarmTerminalPickerDao;
import com.cby.tcs.farm_terminal_picker.service.FarmTerminalPickerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 数据终端和采棉机的关联表 服务实现类
 * </p>
 */
@Service
@RequiredArgsConstructor
public class FarmTerminalPickerServiceImpl extends ServiceImpl<FarmTerminalPickerDao, FarmTerminalPicker> implements FarmTerminalPickerService {

  private final FarmTerminalPickerDao farmTerminalPickerDao;
}
