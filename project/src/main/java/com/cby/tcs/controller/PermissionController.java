package com.cby.tcs.controller;

import com.cby.tcs.permission.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {

  private final PermissionService permissionService;

}
