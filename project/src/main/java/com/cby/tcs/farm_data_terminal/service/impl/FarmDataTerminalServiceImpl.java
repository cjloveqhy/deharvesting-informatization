package com.cby.tcs.farm_data_terminal.service.impl;

import com.cby.tcs.farm_data_terminal.dao.FarmDataTerminalDao;
import com.cby.tcs.farm_data_terminal.entity.po.FarmDataTerminal;
import com.cby.tcs.farm_data_terminal.service.FarmDataTerminalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 农机管理-数据终端 服务实现类
 * </p>
 */
@Service
@RequiredArgsConstructor
public class FarmDataTerminalServiceImpl extends ServiceImpl<FarmDataTerminalDao, FarmDataTerminal> implements FarmDataTerminalService {

  private final FarmDataTerminalDao farmDataTerminalDao;
}
