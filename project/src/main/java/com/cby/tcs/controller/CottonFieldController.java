package com.cby.tcs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cby.tcs.cotton_field.service.CottonFieldService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cotton-field")
@RequiredArgsConstructor
public class CottonFieldController {

  private final CottonFieldService cottonFieldService;

}
