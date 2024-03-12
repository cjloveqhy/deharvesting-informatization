package com.cby.tcs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cby.tcs.farm_cotton_picker.service.FarmCottonPickerService;
import org.springframework.web.bind.annotation.RestController;

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

}
