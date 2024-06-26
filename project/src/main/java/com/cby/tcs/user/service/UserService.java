package com.cby.tcs.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cby.tcs.user.entity.fo.LoginFo;
import com.cby.tcs.user.entity.fo.RegisterUserFo;
import com.cby.tcs.user.entity.po.User;
import com.cby.tcs.user.entity.vo.UserAutoInfo;
import com.cby.tcs.user.entity.vo.UserInfo;
import com.cby.tcs.user.entity.vo.UserOption;
import com.cby.tcs.user.entity.vo.ValidAccountVo;
import com.cby.tcs.user_role.entity.dto.FilterPageUserDTO;
import com.cby.tcs.user_role.entity.fo.UserRolePage;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserService extends IService<User> {

    /**
     * 根据用户id编号获取用户信息
     * @param userIds 用户id编号集合
     * @return {@link List}<{@link UserInfo}>
     */
    List<UserInfo> getUserInfoList(List<String> userIds);

    /**
     * 账户登录
     *
     * @param entity {@link LoginFo} 登录信息体
     * @return {@link UserInfo}
     */
    UserAutoInfo login(LoginFo entity);

    /**
     * 获取验证码
     * @param response
     */
    void getLoginVerifyImg(HttpServletResponse response);

    /**
     * 账户注册
     *
     * @param entity {@link RegisterUserFo} 注册信息
     */
    void register(RegisterUserFo entity);

    /**
     * 根据账户名获取用户信息
     * @param account 账户名
     * @return {@link User}
     */
    User getUserByAccount(String account);

    /**
     * 根据用户名查询是否可用
     * @param account 账户名
     * @return {@link ValidAccountVo}
     */
    ValidAccountVo validAccount(String account);

    /**
     * 获取用户信息
     * @param loginId 当前登录着id编号
     * @return {@link UserInfo}
     */
    UserInfo getUserInfoById(String loginId);

    /**
     * 更新用户信息
     * @param userInfo {@link UserInfo}
     */
    void updateUserInfo(UserInfo userInfo);

    /**
     * 获取所有用户的信息作为可选项，以方便选择用户
     * @return {@link List}<{@link UserOption}>
     */
    List<UserOption> getUserOptions();

    /**
     * 过滤分页获取数据
     *
     * @param entity 过滤数据
     * @return {@link Page}<{@link FilterPageUserDTO}>
     */
    Page<FilterPageUserDTO> filterPage(UserRolePage entity);

    /**
     * 禁止或解除禁止帐户
     * @param userId 用户id编号
     * @return msg
     */
    String forbiddenOrLiftBanAccount(String userId);

}
