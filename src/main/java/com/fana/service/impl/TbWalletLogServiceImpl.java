package com.fana.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fana.config.ResponseResult;
import com.fana.entry.pojo.TbUser;
import com.fana.entry.pojo.TbWalletLog;
import com.fana.entry.vo.IPageVo;
import com.fana.entry.vo.RechargeRecordVo;
import com.fana.mapper.TbCharityMapper;
import com.fana.mapper.TbUserMapper;
import com.fana.mapper.TbWalletLogMapper;
import com.fana.service.ITbWalletLogService;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-08-02
 */
@Service
@Log4j2
public class TbWalletLogServiceImpl extends ServiceImpl<TbWalletLogMapper, TbWalletLog> implements ITbWalletLogService {

    @Resource
    private TbUserMapper userMapper;

    @Resource
    private TbCharityMapper charityMapper;

    @Resource
    private TbWalletLogMapper walletLogMapper;

    /**
     * 查询充值记录
     *
     * @param rechargeRecordVo RechargeRecordVo
     * @return ResponseResult
     */
    @Override
    public ResponseResult queryRechargeRecord(RechargeRecordVo rechargeRecordVo) {
        log.info("FanaWeb|TbWalletLogServiceImpl|queryRechargeRecord|rechargeRecordVo:{}", JSON.toJSONString(rechargeRecordVo));
        IPageVo iPageVo = new IPageVo();
        IPage<TbWalletLog> pageEntity = new Page<>(rechargeRecordVo.getPageNum(), rechargeRecordVo.getPageSize());
        QueryWrapper<TbWalletLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("remark", "top-up").orderByDesc("create_at", "update_at");
        if (StringUtils.isNotBlank(rechargeRecordVo.getEmail())
                || StringUtils.isNotBlank(rechargeRecordVo.getUserName())) {
            List<TbUser> userList = charityMapper.selectUserList(rechargeRecordVo);
            if (CollectionUtils.isNotEmpty(userList)) {
                List<Integer> collect = userList.parallelStream().map(TbUser::getId).collect(Collectors.toList());
                queryWrapper.in("user_id", collect);
                IPage<TbWalletLog> walletLogIPage = walletLogMapper.selectPage(pageEntity, queryWrapper);
                iPageVo = assembleRechargeRecordIPageVo(userList, walletLogIPage);
            }
        } else {
            IPage<TbWalletLog> walletLogIPage = walletLogMapper.selectPage(pageEntity, queryWrapper);
            if (Objects.nonNull(walletLogIPage) && CollectionUtils.isNotEmpty(walletLogIPage.getRecords())) {
                List<Integer> collect = walletLogIPage.getRecords().stream().map(TbWalletLog::getUserId).collect(Collectors.toList());
                List<TbUser> userList = userMapper.selectList(new QueryWrapper<TbUser>().in("id", collect));
                iPageVo = assembleRechargeRecordIPageVo(userList, walletLogIPage);
            }
        }
        log.info("FanaWeb|TbWalletLogServiceImpl|queryRechargeRecord|iPageVo list size:{}", CollectionUtils.size(iPageVo.getList()));
        return ResponseResult.success(iPageVo);
    }

    /**
     * 组装充值记录分页 VO
     *
     * @param userList       List<TbUser>
     * @param walletLogIPage IPage<TbWalletLog>
     * @return IPageVo
     */
    private IPageVo assembleRechargeRecordIPageVo(List<TbUser> userList, IPage<TbWalletLog> walletLogIPage) {
        IPageVo iPageVo = new IPageVo(0L, 0L, 0L, 0L, Lists.newArrayList());
        if (Objects.nonNull(walletLogIPage) && CollectionUtils.isNotEmpty(walletLogIPage.getRecords())) {
            List<RechargeRecordVo> recordVoArrayList = new ArrayList<>();
            walletLogIPage.getRecords().forEach(item -> {
                RechargeRecordVo rechargeRecordVo = new RechargeRecordVo();
                BeanUtils.copyProperties(item, rechargeRecordVo);
                TbUser tbUser = userList.parallelStream().filter(user -> Objects.equals(item.getUserId(), user.getId())).collect(Collectors.toList()).get(0);
                rechargeRecordVo.setUserName(tbUser.getFirstName() + tbUser.getLastName());
                rechargeRecordVo.setEmail(tbUser.getEmail());
                rechargeRecordVo.setFirstName(tbUser.getFirstName());
                rechargeRecordVo.setLastName(tbUser.getLastName());
                rechargeRecordVo.setPageNum(walletLogIPage.getCurrent());
                rechargeRecordVo.setPageSize(walletLogIPage.getSize());
                recordVoArrayList.add(rechargeRecordVo);
            });
            iPageVo = IPageVo.builder().pageNum(walletLogIPage.getCurrent()).pageSize(walletLogIPage.getSize()).total(walletLogIPage.getTotal()).totalPageCount(walletLogIPage.getPages()).list(recordVoArrayList).build();
        }
        return iPageVo;
    }

}
