package com.fana.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fana.config.ResponseResult;
import com.fana.config.Status;
import com.fana.entry.vo.RechargeVo;
import com.fana.exception.CustomException;
import com.fana.service.ITbWalletService;
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
 * @since 2022-08-02
 */
@RestController
@RequestMapping("/tb-wallet")
public class TbWalletController {
    @Resource
    private ITbWalletService walletService;

    @PostMapping("recharge")
    @ApiOperation("钱包充值")
    public ResponseResult recharge(@RequestBody RechargeVo vo){
        if(ObjectUtil.isEmpty(vo)) return new ResponseResult(Status.PARAMETER_ERROR.code, Status.PARAMETER_ERROR.message);
        if (ObjectUtil.isEmpty(vo.getAmount())) return new ResponseResult(Status.PARAMETER_ERROR.code, "Please input your amount.");
        if (vo.getAmount() <= 0) return new ResponseResult(Status.PARAMETER_ERROR.code, "The payment amount must be positive.");
        if (StrUtil.isBlank(vo.getCurrency())) return new ResponseResult(Status.PARAMETER_ERROR.code, "Please input your current.");
        if(ObjectUtil.isEmpty(vo.getUserId())) return new ResponseResult(Status.PARAMETER_ERROR.code, "Please input your userId.");
        return walletService.recharge(vo);
    }

}
