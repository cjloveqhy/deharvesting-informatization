package com.cby.tcs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.common.entity.BasicOption;
import com.cby.tcs.farm_data_terminal.entity.fo.DataTerminalFo;
import com.cby.tcs.farm_data_terminal.entity.fo.DataTerminalPageFo;
import com.cby.tcs.farm_data_terminal.entity.vo.DataTerminalVo;
import com.cby.tcs.farm_data_terminal.service.FarmDataTerminalService;
import com.freedom.cloud.annotation.ParamsToEntity;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  /**
   * 获取所有品牌选项
   */
  @GetMapping("/brandOptions")
  public ResultEntity getBrandOptions() {
    List<BasicOption> options = farmDataTerminalService.getBrandOptions();
    return ResultEntity.success(options);
  }

  /**
   * 获取过滤分页的数据
   */
  @GetMapping("/filterPage")
  public ResultEntity getFilterPage(@ParamsToEntity(required = false) DataTerminalPageFo entity) {
    Page<DataTerminalVo> voPage = farmDataTerminalService.getFilterPage(entity);
    return ResultEntity.success(voPage);
  }

  /**
   * 新机入库（添加数据终端）
   */
  @PostMapping("/add")
  public ResultEntity addFarmDataTerminal(@RequestBody @Validated DataTerminalFo entity) {
    farmDataTerminalService.addFarmDataTerminal(entity);
    return ResultEntity.success("添加成功");
  }

  /**
   * 修改数据终端信息
   */
  @PutMapping("/update")
  public ResultEntity updateFarmDataTerminal(@RequestBody @Validated DataTerminalFo entity) {
    farmDataTerminalService.updateFarmDataTerminal(entity);
    return ResultEntity.success("修改成功");
  }

  /**
   * 删除数据终端信息
   */
  @DeleteMapping("/del")
  public ResultEntity delFarmDataTerminal(@RequestParam String id) {
    farmDataTerminalService.delFarmDataTerminal(id);
    return ResultEntity.success("删除成功");
  }

}
