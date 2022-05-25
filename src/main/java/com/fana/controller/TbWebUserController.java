package com.fana.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fana.config.ResponseResult;
import com.fana.config.Status;
import com.fana.entry.vo.LoginVo;
import com.fana.entry.vo.WebUserVo;
import com.fana.exception.CustomException;
import com.fana.service.ITbUserService;
import com.fana.service.ITbWebUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2022-05-25
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户管理模块")
public class TbWebUserController {
    @Resource
    private ITbWebUserService webUserService;

    @PostMapping("/login")
    @ApiOperation(value = "登录接口")
    public ResponseResult login(@RequestBody LoginVo vo){
        if(ObjectUtil.isEmpty(vo)) throw new CustomException(Status.PARAMETER_ERROR.code,Status.PARAMETER_ERROR.message);
        if(StrUtil.isBlank(vo.getUsername())) throw new CustomException(Status.PARAMETER_ERROR.code,"Please fill in the correct username address or password.");
        if(StrUtil.isBlank(vo.getPassword())) throw new CustomException(Status.PARAMETER_ERROR.code,"Please fill in the correct username address or password.");
        return webUserService.login(vo);
    }

    /**
     * 获取用户列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "用户列表")
    public ResponseResult getList(@RequestBody WebUserVo vo){
        if(ObjectUtil.isEmpty(vo)) {
            return new ResponseResult(Status.PARAMETER_ERROR.code, Status.PARAMETER_ERROR.message);
        }
        if(vo.getPageNum() == null){
            return new ResponseResult(Status.PARAMETER_ERROR.code, "The pageNum did not fill in  ");
        }

        if(vo.getPageSize() == null){
            return new ResponseResult(Status.PARAMETER_ERROR.code, "The pageSize did not fill in  ");
        }

        return webUserService.getList(vo);
    }

    /**
     * 修改用户
     */
    @PostMapping("/update")
    @ApiOperation(value = "用户列表")
    public ResponseResult updateUser(@RequestBody WebUserVo vo){
        return webUserService.updateUser(vo);
    }

    /**
     * 用户查询
     */
    @PostMapping("/select")
    @ApiOperation(value = "用户详情")
    public ResponseResult selectUser(@RequestBody WebUserVo vo){
        return webUserService.selectUser(vo);
    }

    /**
     * 删除用户
     */
    @PostMapping("/delete")
    @ApiOperation(value = "用户详情")
    public ResponseResult deleteUser(@RequestBody WebUserVo vo){
        return webUserService.deleteUser(vo);
    }

}
