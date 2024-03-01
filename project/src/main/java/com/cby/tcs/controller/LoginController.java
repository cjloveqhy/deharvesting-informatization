package com.cby.tcs.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.cby.tcs.user.entity.fo.LoginFo;
import com.cby.tcs.user.entity.fo.RegisterUserFo;
import com.cby.tcs.user.entity.vo.UserAutoInfo;
import com.cby.tcs.user.entity.vo.UserInfo;
import com.cby.tcs.user.entity.vo.ValidAccountVo;
import com.cby.tcs.user.service.UserService;
import com.freedom.cloud.result.ResultEntity;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class LoginController {

  private final UserService userService;

  /**
   * 登录
   */
  @PostMapping("/login")
  public ResultEntity login(@Validated @RequestBody LoginFo entity) {
    UserAutoInfo info = userService.login(entity);
    return ResultEntity.success(info);
  }

  /**
   * 注册
   */
  @PostMapping("/register")
  public ResultEntity register(@Validated @RequestBody RegisterUserFo entity) {
    userService.register(entity);
    return ResultEntity.success(String.format("账户%s注册成功", entity.getAccount()));
  }

  /**
   * 退出
   */
  @GetMapping("/logout")
  public ResultEntity logout() {
    StpUtil.logout(StpUtil.getLoginId());
    return ResultEntity.success("推出成功");
  }

  /**
   * 验证账户名是否存在
   */
  @GetMapping("/validAccount")
  public ResultEntity validAccount(@RequestParam String account) {
    ValidAccountVo vo = userService.validAccount(account);
    return ResultEntity.success(vo);
  }

  /**
   * 获取用户信息
   */
  @GetMapping("/getUserInfo")
  public ResultEntity getUserInfo() {
    UserInfo userInfo = userService.getUserInfoById(StpUtil.getLoginIdAsString());
    return ResultEntity.success(userInfo);
  }

  /**
   * 更新用户信息
   */
  @PutMapping("/updateUserInfo")
  public ResultEntity updateUserInfo(@RequestBody @Validated UserInfo userInfo) {
    userService.updateUserInfo(userInfo);
    return ResultEntity.success("更新成功");
  }

  /**
   * 获取登录验证图片
   */
  @GetMapping("/getLoginVerifyImg")
  public void getLoginVerifyImg(HttpServletResponse response) {
    userService.getLoginVerifyImg(response);
  }

}
