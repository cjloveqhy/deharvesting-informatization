package com.cby.tcs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cby.tcs.farm_uav.service.FarmUavService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 农机管理-无人机 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/farm-uav")
@RequiredArgsConstructor
public class FarmUavController {

  private final FarmUavService farmUavService;

}
