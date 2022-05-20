package com.fana.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fana.config.ResponseResult;
import com.fana.config.Status;
import com.fana.entry.vo.LoginVo;
import com.fana.exception.CustomException;
import com.fana.service.ITbUserService;
import com.fana.utils.RegexUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@Api(value = "用户管理模块")
public class UserController {

    @Resource
    private ITbUserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "登录接口")
    public ResponseResult login(@RequestBody LoginVo vo){
        if(ObjectUtil.isEmpty(vo)) throw new CustomException(Status.PARAMETER_ERROR.code,Status.PARAMETER_ERROR.message);
        if(StrUtil.isBlank(vo.getUsername())) throw new CustomException(Status.PARAMETER_ERROR.code,"Please fill in the correct username address or password.");
        if(StrUtil.isBlank(vo.getPassword())) throw new CustomException(Status.PARAMETER_ERROR.code,"Please fill in the correct username address or password.");
        return userService.login(vo);
    }


}
