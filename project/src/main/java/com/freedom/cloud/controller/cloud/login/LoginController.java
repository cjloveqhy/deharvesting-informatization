package com.freedom.cloud.controller.cloud.login;

import cn.dev33.satoken.stp.StpUtil;
import com.freedom.cloud.api.login.entity.fo.PasswordLoginEntity;
import com.freedom.cloud.api.login.entity.vo.UserAutoInfo;
import com.freedom.cloud.api.login.service.LoginService;
import com.freedom.cloud.enums.LoginEnum;
import com.freedom.cloud.result.ResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    /**
     * 密码登录
     */
    @PostMapping("/pwd")
    public ResultEntity passwordLogin(@RequestBody PasswordLoginEntity entity) {
        UserAutoInfo userAutoInfo = loginService.passwordLogin(entity.setType(LoginEnum.PASSWORD));
        return ResultEntity.success(userAutoInfo);
    }

    /**
     * 退出
     */
    @GetMapping("/logout")
    public ResultEntity logout() {
        String msg = loginService.logout((String) StpUtil.getLoginId(), StpUtil.getLoginDevice());
        return ResultEntity.success(msg);
    }

}
