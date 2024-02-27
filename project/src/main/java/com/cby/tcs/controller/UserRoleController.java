package com.cby.tcs.controller;

import com.cby.tcs.user_role.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-role")
@RequiredArgsConstructor
public class UserRoleController {

  private final UserRoleService userRoleService;

}
