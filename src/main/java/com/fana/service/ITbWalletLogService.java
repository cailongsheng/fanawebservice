package com.fana.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fana.config.ResponseResult;
import com.fana.entry.pojo.TbWalletLog;
import com.fana.entry.vo.RechargeRecordVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-08-02
 */
public interface ITbWalletLogService extends IService<TbWalletLog> {

    /**
     * 查询充值记录
     *
     * @param rechargeRecordVo RechargeRecordVo
     * @return ResponseResult
     */
    ResponseResult queryRechargeRecord(RechargeRecordVo rechargeRecordVo);

}
