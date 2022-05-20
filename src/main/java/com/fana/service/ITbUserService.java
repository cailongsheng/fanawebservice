package com.fana.service;

import com.fana.config.ResponseResult;
import com.fana.entry.pojo.TbUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fana.entry.vo.LoginVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-05-11
 */
public interface ITbUserService extends IService<TbUser> {

    ResponseResult login(LoginVo vo);


}
