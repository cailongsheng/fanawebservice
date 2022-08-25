package com.fana.service;

import com.fana.config.ResponseResult;
import com.fana.entry.pojo.TbWallet;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fana.entry.vo.RechargeVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-08-02
 */
public interface ITbWalletService extends IService<TbWallet> {
    /**
     * 钱包充值(web)
     * @param vo
     * @return
     */
    ResponseResult recharge(RechargeVo vo);
}
