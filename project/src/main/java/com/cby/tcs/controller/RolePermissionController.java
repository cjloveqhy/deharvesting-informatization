package com.cby.tcs.controller;

import com.cby.tcs.role_permission.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role-permission")
@RequiredArgsConstructor
public class RolePermissionController {

  private final RolePermissionService rolePermissionService;

}
