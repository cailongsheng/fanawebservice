package com.fana.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fana.config.ResponseResult;
import com.fana.config.Status;
import com.fana.entry.pojo.TbWebUser;
import com.fana.entry.vo.IPageVo;
import com.fana.entry.vo.LoginVo;
import com.fana.entry.vo.WebUserVo;
import com.fana.exception.CustomException;
import com.fana.mapper.TbWebUserMapper;
import com.fana.service.ITbWebUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fana.utils.LogUtil;
import com.fana.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-05-25
 */
@Slf4j
@Service
public class TbWebUserServiceImpl extends ServiceImpl<TbWebUserMapper, TbWebUser> implements ITbWebUserService {

    @Resource
    private TbWebUserMapper webUserMapper;

    @Override
    public ResponseResult login(LoginVo vo) {

        TbWebUser tbWebUser = webUserMapper.selectOne(new QueryWrapper<TbWebUser>()
                .lambda().eq(TbWebUser::getUsername, vo.getUsername()));
        if (ObjectUtil.isEmpty(tbWebUser)) {
            log.info("用户不存在...");
            throw new CustomException(Status.USER_VOID.code, Status.USER_VOID.message);
        }
        if (!MD5Util.inputPassToDbPass(vo.getPassword()).equals(tbWebUser.getPassword())) {
            log.info("密码错误...");
            throw new CustomException(Status.PASSWORD_ERROR.code, Status.PASSWORD_ERROR.message);
        }
        LoginVo response = LoginVo.builder()
                .username(tbWebUser.getUsername())
                .id(tbWebUser.getId().toString())
                .roles(tbWebUser.getRoleId())
                .build();
        return ResponseResult.success(response);
    }

    @Override
    public ResponseResult getList(WebUserVo vo) {
        LogUtil.addInfoLog("获取用户列表", "/user/list", JSON.toJSON(vo));
        IPage<TbWebUser> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        QueryWrapper<TbWebUser> queryWrapper = new QueryWrapper<>();
        IPage<TbWebUser> iPage = webUserMapper.selectPage(page, queryWrapper);
        IPageVo build = IPageVo.builder().total(iPage.getTotal()).pageSize(iPage.getSize()).pageNum(iPage.getCurrent()).dataList(iPage.getRecords()).build();
        return ResponseResult.success(build);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateUser(WebUserVo vo) {
        LogUtil.addInfoLog("修改用户信息", "/user/update", JSON.toJSON(vo));
        TbWebUser webUser = TbWebUser.builder().build();
        TbWebUser tbWebUser = webUserMapper.selectById(vo.getId());
        if (!MD5Util.inputPassToDbPass(vo.getPassword()).equals(tbWebUser.getPassword())) {
            webUser = TbWebUser.builder().id(vo.getId()).username(vo.getUsername()).password(MD5Util.inputPassToDbPass(vo.getPassword())).roleId(vo.getRoleId()).build();
        } else {
            webUser = TbWebUser.builder().id(vo.getId()).username(vo.getUsername()).roleId(vo.getRoleId()).build();
        }
        try {
            webUserMapper.updateById(webUser);
        } catch (Exception e) {
            LogUtil.addErrorLog("修改用户信息error", "/user/update", e.getMessage());
            throw new CustomException(201, e.getMessage());
        }
        return ResponseResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult selectUser(WebUserVo vo) {
        LogUtil.addInfoLog("获取用户信息详情", "/user/select", JSON.toJSON(vo));
        TbWebUser tbWebUser = webUserMapper.selectById(vo.getId());
        return ResponseResult.success(tbWebUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteUser(WebUserVo vo) {
        LogUtil.addInfoLog("delete用户信息详情", "/user/delete", JSON.toJSON(vo));
        try {
            webUserMapper.deleteById(vo.getId());
        } catch (Exception e) {
            LogUtil.addErrorLog("delete用户信息详情error", "/user/delete", e.getMessage());
            throw new CustomException(201, e.getMessage());
        }
        return ResponseResult.success();
    }


}
