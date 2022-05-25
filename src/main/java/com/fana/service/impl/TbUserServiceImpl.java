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



}
