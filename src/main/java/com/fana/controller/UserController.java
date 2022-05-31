package com.fana.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fana.config.ResponseResult;
import com.fana.config.Status;
import com.fana.entry.vo.AppUserVo;
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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user/app")
@Api(value = "app用户管理模块弃用")
public class UserController {
    @Resource
    ITbUserService userService;
    /**
     * 获取用户列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "用户列表")
    public ResponseResult getList(@RequestBody AppUserVo vo){
        if(ObjectUtil.isEmpty(vo)) {
            return new ResponseResult(Status.PARAMETER_ERROR.code, Status.PARAMETER_ERROR.message);
        }
        if(vo.getPageNum() == null){
            return new ResponseResult(Status.PARAMETER_ERROR.code, "The pageNum did not fill in  ");
        }

        if(vo.getPageSize() == null){
            return new ResponseResult(Status.PARAMETER_ERROR.code, "The pageSize did not fill in  ");
        }

        return userService.getList(vo);
    }

    /**
     * 修改用户
     */
    @PostMapping("/update")
    @ApiOperation(value = "用户列表")
    public ResponseResult updateUser(@RequestBody AppUserVo vo){
        return userService.updateUser(vo);
    }

    /**
     * 用户查询
     */
    @PostMapping("/select")
    @ApiOperation(value = "用户详情")
    public ResponseResult selectUser(@RequestBody AppUserVo vo){
        return userService.selectUser(vo);
    }

    /**
     * 删除用户
     */
    @PostMapping("/delete")
    @ApiOperation(value = "用户详情")
    public ResponseResult deleteUser(@RequestBody AppUserVo vo){
        return userService.deleteUser(vo);
    }

    /**
     * 添加用户
     */
    @PostMapping("/add")
    @ApiOperation(value = "用户详情")
    public ResponseResult addUser(@RequestBody AppUserVo vo){
        return userService.addUser(vo);
    }

    /**
     * 用户上传头像
     */
    @PostMapping("/upload")
    @ApiOperation(value = "web用户上传头像")
    public ResponseResult uploadUserImage(MultipartFile file){
        return userService.uploadUserImage(file);
    }
}
