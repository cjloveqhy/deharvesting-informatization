package com.cby.tcs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cby.tcs.ginnery.service.GinneryService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ginnery")
@RequiredArgsConstructor
public class GinneryController {

  private final GinneryService ginneryService;

}
