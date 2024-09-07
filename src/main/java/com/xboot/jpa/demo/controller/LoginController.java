package com.xboot.jpa.demo.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.xboot.jpa.demo.common.resp.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 注释
 *
 * @author xboot
 **/
@RestController
@RequestMapping("/auth")
public class LoginController {

    @RequestMapping("/login")
    public Result login() {
        StpUtil.login(10000);
        String tokenValue = StpUtil.getTokenValueByLoginId(10000);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        List<String> permissionList = StpUtil.getPermissionList();
        Map<String, Object> result = new java.util.HashMap<>();
        result.put("token", tokenValue);
        result.put("tokenInfo", tokenInfo);
        result.put("permissions", permissionList);
        result.put("isLogin", StpUtil.isLogin());
        result.put("roles", StpUtil.getRoleList());
        return Result.ok().data(result);
    }
}
