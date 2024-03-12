package com.cby.tcs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cby.tcs.farm_data_terminal.service.FarmDataTerminalService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 农机管理-数据终端 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/farm-data-terminal")
@RequiredArgsConstructor
public class FarmDataTerminalController {

  private final FarmDataTerminalService farmDataTerminalService;

}
