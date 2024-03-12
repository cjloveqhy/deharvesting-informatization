package com.cby.tcs.farm_terminal_picker.dao;

import com.cby.tcs.farm_terminal_picker.entity.po.FarmTerminalPicker;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 数据终端和采棉机的关联表 Mapper 接口
 * </p>
 */
@Mapper
public interface FarmTerminalPickerDao extends BaseMapper<FarmTerminalPicker> {

}
