package com.fana.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fana.config.ResponseResult;
import com.fana.entry.pojo.TbOrder;
import com.fana.entry.pojo.TbWallet;
import com.fana.entry.pojo.TbWalletLog;
import com.fana.entry.vo.RechargeVo;
import com.fana.mapper.TbOrderMapper;
import com.fana.mapper.TbWalletLogMapper;
import com.fana.mapper.TbWalletMapper;
import com.fana.service.ITbWalletService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fana.utils.SnowFlow;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-08-02
 */
@Service
@Log4j2
public class TbWalletServiceImpl extends ServiceImpl<TbWalletMapper, TbWallet> implements ITbWalletService {
    @Resource
    private TbOrderMapper orderMapper;

    @Resource
    private TbWalletLogMapper walletLogMapper;

    @Resource
    private TbWalletMapper walletMapper;

    /**
     * 钱包充值 web
     * @param vo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult recharge(RechargeVo vo) {
        log.info("*******web 钱包充值*******{}",vo.getUserId());
        //校验tb_wallet表 数据是否存在
        TbWallet tbWallet = walletMapper.selectOne(new QueryWrapper<TbWallet>().lambda()
                .eq(TbWallet::getUserId, vo.getUserId()));
        BigDecimal amount = new BigDecimal(vo.getAmount()).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal balance = new BigDecimal("0.00");
        log.info("wallet充值{}",vo.getUserId());
        if(ObjectUtil.isEmpty(tbWallet)){
            TbWallet build = TbWallet.builder()
                    .userId(vo.getUserId())
                    .balance(amount)
                    .createAt(LocalDateTime.now())
                    .updateAt(LocalDateTime.now())
                    .build();
            walletMapper.insert(build);
        }else{
            balance = tbWallet.getBalance();
            tbWallet.setBalance(tbWallet.getBalance().add(amount));
            tbWallet.setUpdateAt(LocalDateTime.now());
            walletMapper.updateById(tbWallet);
        }
        //order记录
        SnowFlow snowFlow = new SnowFlow(1, 1, 1);
        TbOrder build = TbOrder.builder()
                .orderNo(String.valueOf(snowFlow.nextId()))
                .payStatus("SUCCESS")
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .currency(vo.getCurrency())
                .submitTime(LocalDateTime.now())
                .totalAmount(amount)
                .userId(vo.getUserId())
                .webHookStatus("NOTNOTICES")
                .type("WEB_RECHARGE")
                .build();
        orderMapper.insert(build);

        TbWalletLog build1 = TbWalletLog.builder()
                .balance(balance)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .totalAmount(amount)
                .remark("top-up")
                .orderNo(build.getOrderNo())
                .userId(vo.getUserId())
                .payStatus("SUCCESS").build();
        walletLogMapper.insert(build1);

        return ResponseResult.success();
    }
}
