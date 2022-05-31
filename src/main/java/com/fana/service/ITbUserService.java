package com.fana.service;

import com.fana.config.ResponseResult;
import com.fana.entry.pojo.TbUser;
import com.fana.entry.pojo.TbWebUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fana.entry.vo.AppUserVo;
import com.fana.entry.vo.LoginVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-05-11
 */
public interface ITbUserService extends IService<TbUser> {


    ResponseResult getList(AppUserVo vo);

    ResponseResult updateUser(AppUserVo vo);

    ResponseResult selectUser(AppUserVo vo);

    ResponseResult deleteUser(AppUserVo vo);

    ResponseResult addUser(AppUserVo vo);

    ResponseResult uploadUserImage(MultipartFile file);
}
