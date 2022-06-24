package com.fana.controller;


import com.fana.config.ResponseResult;
import com.fana.entry.pojo.TbUserMoney;
import com.fana.entry.vo.ApiUserMoneyVo;
import com.fana.service.ITbUserMoneyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2022-06-23
 */
@RestController
@RequestMapping("/user/money")
@CrossOrigin
@Api("用户捐款钱包")
public class TbUserMoneyController {
    @Resource
    ITbUserMoneyService userMoneyService;

    @ApiOperation("添加用户钱包信息")
    @PostMapping("/add")
    public ResponseResult addUserMoney(@RequestBody ApiUserMoneyVo money){
        return userMoneyService.addUserMoney(money);
    }

    @ApiOperation("修改用户钱包信息时校验用户名")
    @PostMapping("/check")
    public ResponseResult checkUserMoney(@RequestBody ApiUserMoneyVo money){
        return userMoneyService.checkUserMoney(money);
    }

    @ApiOperation("获取用户钱包信息列表")
    @PostMapping("/list")
    public ResponseResult getUserMoneyList(@RequestBody ApiUserMoneyVo money){
        return  userMoneyService.getUserMoneyList(money);
    }

    @ApiOperation("删除用户钱包信息")
    @PostMapping("/delete")
    public ResponseResult deleteUserMoney(@RequestBody ApiUserMoneyVo money){
        return userMoneyService.deleteUserMoney(money);
    }
}
