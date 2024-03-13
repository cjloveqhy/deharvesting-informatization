package com.cby.tcs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cby.tcs.common.entity.BasicOption;
import com.cby.tcs.farm_cotton_picker.entity.fo.CottonPickerFo;
import com.cby.tcs.farm_cotton_picker.entity.fo.CottonPickerPageFo;
import com.cby.tcs.farm_cotton_picker.entity.vo.CottonPickerVo;
import com.cby.tcs.farm_cotton_picker.service.FarmCottonPickerService;
import com.freedom.cloud.annotation.ParamsToEntity;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 农机管理-采棉机 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/farm-cotton-picker")
@RequiredArgsConstructor
public class FarmCottonPickerController {

  private final FarmCottonPickerService farmCottonPickerService;

  /**
   * 获取所有品牌选项
   */
  @GetMapping("/brandOptions")
  public ResultEntity getBrandOptions() {
    List<BasicOption> options = farmCottonPickerService.getBrandOptions();
    return ResultEntity.success(options);
  }

  /**
   * 获取过滤分页的数据
   */
  @GetMapping("/filterPage")
  public ResultEntity getFilterPage(@ParamsToEntity(required = false) CottonPickerPageFo entity) {
    Page<CottonPickerVo> voPage = farmCottonPickerService.getFilterPage(entity);
    return ResultEntity.success(voPage);
  }

  /**
   * 新机入库（新增采棉机）
   */
  @PostMapping("/add")
  public ResultEntity addFarmCottonPicker(@RequestBody @Validated CottonPickerFo entity) {
    farmCottonPickerService.addFarmCottonPicker(entity);
    return ResultEntity.success("添加成功");
  }

  /**
   * 移除采棉机
   */
  @DeleteMapping("/del")
  public ResultEntity delFarmCottonPicker(@RequestParam String id) {
    farmCottonPickerService.delFarmCottonPicker(id);
    return ResultEntity.success("删除成功");
  }

  /**
   * 更新采棉机设备信息
   */
  @PutMapping("/update")
  public ResultEntity updateFarmCottonPicker(@RequestBody @Validated CottonPickerFo entity) {
    farmCottonPickerService.updateFarmCottonPicker(entity);
    return ResultEntity.success("修改成功");
  }

}
