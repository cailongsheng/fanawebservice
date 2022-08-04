package com.fana;

import com.alibaba.fastjson.JSON;
import com.fana.config.ResponseResult;
import com.fana.entry.vo.RechargeRecordVo;
import com.fana.service.ITbWalletLogService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class WalletLogServiceTests {

    @Resource
    ITbWalletLogService walletLogService;

    @Test
    void queryRechargeRecordTest() {
        RechargeRecordVo rechargeRecordVo = new RechargeRecordVo();
        rechargeRecordVo.setPageNum(1L).setPageSize(2L).setUserName("RobinYan").setEmail("obin");
        ResponseResult responseResult = walletLogService.queryRechargeRecord(rechargeRecordVo);
        System.out.println(JSON.toJSONString(responseResult));
    }
}
