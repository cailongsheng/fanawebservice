package com.fana.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fana.config.ResponseResult;
import com.fana.entry.pojo.TbUser;
import com.fana.entry.pojo.TbUserMoney;
import com.fana.entry.vo.ApiUserMoneyVo;
import com.fana.entry.vo.IPageVo;
import com.fana.exception.CustomException;
import com.fana.mapper.TbUserMapper;
import com.fana.mapper.TbUserMoneyMapper;
import com.fana.service.ITbUserMoneyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fana.utils.LogUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-06-23
 */
@Service
public class TbUserMoneyServiceImpl extends ServiceImpl<TbUserMoneyMapper, TbUserMoney> implements ITbUserMoneyService {
    @Resource
    TbUserMoneyMapper userMoneyMapper;
    @Resource
    TbUserMapper userMapper;

    @Override
    public ResponseResult addUserMoney(ApiUserMoneyVo money) {
        LogUtil.addInfoLog("添加用户捐款","/user/money/add", JSON.toJSON(money));
        int id = 0;
        try {
            id = userMoneyMapper.insert(TbUserMoney.builder()
                    .charityId(ObjectUtil.isNull(money.getCharityId()) ? -1 : money.getCharityId())
                    .charityName(StrUtil.isBlank(money.getCharityName()) ? "-" : money.getCharityName())
                    .money(ObjectUtil.isNull(money.getMoney()) ? new BigDecimal("0.00") : money.getMoney())
                    .userId(ObjectUtil.isNull(money.getUserId()) ? -1 : money.getUserId())
                    .username(StrUtil.isBlank(money.getUsername()) ? "-" : money.getUsername())
                    .build());
        } catch (Exception e) {
            LogUtil.addInfoLog("添加用户捐款error","/user/money/add", JSON.toJSON(e.getMessage()));
            throw new CustomException(201,e.getMessage());
        }
        return ResponseResult.success(id);
    }

    @Override
    public ResponseResult checkUserMoney(ApiUserMoneyVo money) {
        QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(money.getUsername()))queryWrapper.eq("email",money.getUsername());
        else return ResponseResult.error(201,"username not exist");
        TbUser tbUser = userMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNotNull(tbUser)) return ResponseResult.success(true);
        return ResponseResult.success(false);
    }

    @Override
    public ResponseResult getUserMoneyList(ApiUserMoneyVo money) {
        if (ObjectUtil.isNull(money.getPageNum())) money.setPageNum(1);
        if (ObjectUtil.isNull(money.getPageSize())) money.setPageSize(10);
        IPage page = new Page(money.getPageNum(),money.getPageSize());
        QueryWrapper<TbUserMoney> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(money.getSearch())) queryWrapper.ne("is_delete",1).like("username",money.getSearch()).or()
        .like("charity_name",money.getSearch()).orderByDesc("create_at");
        IPage selectPage = userMoneyMapper.selectPage(page, queryWrapper);
        IPageVo build = IPageVo.builder().pageNum(selectPage.getCurrent()).pageSize(selectPage.getSize()).total(selectPage.getTotal()).list(selectPage.getRecords()).build();
        return ResponseResult.success(JSON.toJSON(build));
    }

    @Override
    public ResponseResult deleteUserMoney(ApiUserMoneyVo money) {
        UpdateWrapper<TbUserMoney> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",money.getId());
        updateWrapper.set("is_delete",money.getIsDelete());
        int update = userMoneyMapper.update(TbUserMoney.builder().id(money.getId()).build(), updateWrapper);
        return ResponseResult.success(update);
    }
}
