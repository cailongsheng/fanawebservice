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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
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
            LogUtil.addErrorLog("添加用户捐款error","/user/money/add", JSON.toJSON(e.getMessage()));
            throw new CustomException(201,e.getMessage());
        }
        return ResponseResult.success(id);
    }

    @Override
    public ResponseResult checkUserMoney(ApiUserMoneyVo money) {
        LogUtil.addInfoLog("验证用户名是否存在","/user/money/check", JSON.toJSON(money));
        QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",money.getUsername());
        TbUser tbUser = userMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNotNull(tbUser)) return ResponseResult.success(tbUser);
        return new ResponseResult(200,"username is not exist");
    }

    @Override
    public ResponseResult getUserMoneyList(ApiUserMoneyVo money) {
        LogUtil.addInfoLog("获取用户捐款数据列表","/user/money/list", JSON.toJSON(money));
        if (ObjectUtil.isNull(money.getPageNum())) money.setPageNum(1);
        if (ObjectUtil.isNull(money.getPageSize())) money.setPageSize(10);
        IPage page = new Page(money.getPageNum(),money.getPageSize());
        QueryWrapper<TbUserMoney> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(money.getSearch())) queryWrapper.ne("is_delete",1).like("username",money.getSearch()).or()
        .like("charity_name",money.getSearch()).orderByDesc("create_at");
        IPage selectPage = userMoneyMapper.selectPage(page, queryWrapper);
        IPageVo build = IPageVo.builder().pageNum(selectPage.getCurrent()).pageSize(selectPage.getSize()).total(selectPage.getTotal()).list(selectPage.getRecords()).build();
        LogUtil.returnInfoLog("获取用户捐款数据列表(返回数据)","/user/money/list", JSON.toJSON(money));
        return ResponseResult.success(JSON.toJSON(build));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteUserMoney(ApiUserMoneyVo money) {
        LogUtil.addInfoLog("删除用户捐款数据","/user/money/delete", JSON.toJSON(money));
        UpdateWrapper<TbUserMoney> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",money.getId());
        updateWrapper.set("is_delete",money.getIsDelete());
        int update = userMoneyMapper.update(TbUserMoney.builder().id(money.getId()).build(), updateWrapper);
        return ResponseResult.success(update);
    }
}
