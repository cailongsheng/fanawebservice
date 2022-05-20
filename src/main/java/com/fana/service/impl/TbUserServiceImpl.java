package com.fana.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fana.config.ResponseResult;
import com.fana.config.Status;
import com.fana.entry.pojo.TbWebUser;
import com.fana.entry.vo.LoginVo;
import com.fana.exception.CustomException;
import com.fana.mapper.TbUserMapper;
import com.fana.service.ITbUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fana.utils.MD5Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-05-11
 */
@Service
@Log4j2
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbWebUser> implements ITbUserService {
    @Resource
    private TbUserMapper userMapper;

    @Override
    public ResponseResult login(LoginVo vo) {

        TbWebUser tbWebUser = userMapper.selectOne(new QueryWrapper<TbWebUser>()
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


}
