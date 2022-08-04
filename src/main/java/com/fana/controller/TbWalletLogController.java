package com.fana.controller;


import com.fana.config.ResponseResult;
import com.fana.entry.vo.RechargeRecordVo;
import com.fana.service.ITbWalletLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2022-08-02
 */
@RestController
@RequestMapping("/tb-wallet-log")
public class TbWalletLogController {

    @Resource
    private ITbWalletLogService walletLogService;

    /**
     * 查询充值记录
     *
     * @param rechargeRecordVo RechargeRecordVo
     * @return ResponseResult
     */
    @PostMapping("/queryRechargeRecord")
    @ApiOperation("查询充值记录")
    public ResponseResult queryRechargeRecord(@RequestBody RechargeRecordVo rechargeRecordVo) {
        return walletLogService.queryRechargeRecord(rechargeRecordVo);
    }

}
