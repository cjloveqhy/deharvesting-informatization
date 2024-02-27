package com.cby.tcs.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cby.tcs.exception.UserException;
import com.cby.tcs.user.dao.UserDao;
import com.cby.tcs.user.entity.fo.LoginFo;
import com.cby.tcs.user.entity.fo.RegisterUserFo;
import com.cby.tcs.user.entity.po.User;
import com.cby.tcs.user.entity.vo.UserAutoInfo;
import com.cby.tcs.user.entity.vo.UserInfo;
import com.cby.tcs.user.entity.vo.ValidAccountVo;
import com.cby.tcs.user.service.UserService;
import com.cby.tcs.utils.IPUtil;
import com.cby.tcs.utils.RedisUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

  private final UserDao userDao;

  private final RedisUtils redisUtils;

  @Override
  public List<UserInfo> getUserInfoList(List<String> userIds) {
    if (Objects.isNull(userIds) || userIds.isEmpty()) return Collections.emptyList();
    List<User> users = listByIds(userIds);
    return BeanUtil.copyToList(users, UserInfo.class);
  }

  @Override
  public UserAutoInfo login(LoginFo entity) {
    User user = getUserByAccount(entity.getAccount());
    if (Objects.isNull(user) || !DigestUtil.md5Hex(entity.getPassword()).equals(user.getPassword())) throw new UserException("账户名或密码输入错误");
    if (!getLoginVerifyCode().equals(entity.getCode())) throw new UserException("验证码输入错误");
    if (!StpUtil.isLogin()) StpUtil.login(user.getId());
    UserAutoInfo info = new UserAutoInfo();
    info.setInfo(BeanUtil.copyProperties(user, UserInfo.class))
            .setId(user.getId())
            .setToken(StpUtil.getTokenValue())
            .setAccount(user.getAccount());
    return info;
  }

  /**
   * 获取登录验证图片
   */
  @Override
  public void getLoginVerifyImg(HttpServletResponse response) {
    //定义图形验证码的长、宽、验证码字符数、干扰元素个数
    LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(180, 60, 4, 100);
    //图形验证码写出，可以写出到文件，也可以写出到流
    try {
      lineCaptcha.write(response.getOutputStream());
      // 添加到redis缓存中
      String code = lineCaptcha.getCode();
      String key = getLoginVerifyRedisCodeKey();
      redisUtils.set(key, code, DateUnit.MINUTE.getMillis() * 2);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void register(RegisterUserFo entity) {
    User user = getUserByAccount(entity.getAccount());
    if (Objects.nonNull(user)) throw new UserException("该账户名已存在，请重新设置账户名");
    if (!entity.getPassword().equals(entity.getRePassword())) throw new UserException("两次密码不相同，请检查");
    save(new User().setAccount(entity.getAccount()).setPassword(DigestUtil.md5Hex(entity.getPassword())));
  }

  @Override
  public User getUserByAccount(String account) {
    LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(User::getAccount, account);
    return getOne(wrapper);
  }

  @Override
  public ValidAccountVo validAccount(String account) {
    User user = getUserByAccount(account);
    ValidAccountVo accountVo = new ValidAccountVo().setUsable(false);
    if (Objects.isNull(user)) {
      accountVo.setMessage("该账户名可用").setUsable(true);
    } else {
      accountVo.setMessage("该账户名已存在");
    }
    return accountVo;
  }

  /**
   * 获取验证码
   */
  public String getLoginVerifyCode() {
    String key = getLoginVerifyRedisCodeKey();
    return redisUtils.get(key, 0);
  }

  private String getLoginVerifyRedisCodeKey() {
    String ip = IPUtil.getIpAddr();
    return "loginVerifyCode" + ip;
  }
}
