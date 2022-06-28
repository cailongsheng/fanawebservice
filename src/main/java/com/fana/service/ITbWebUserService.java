package com.fana.service;

import com.fana.config.ResponseResult;
import com.fana.entry.pojo.TbWebUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fana.entry.vo.LoginVo;
import com.fana.entry.vo.WebUserVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-05-25
 */
public interface ITbWebUserService extends IService<TbWebUser> {
    ResponseResult login(LoginVo vo);

    ResponseResult getList(WebUserVo vo);

    ResponseResult updateUser(WebUserVo vo, String Authorization);

    ResponseResult selectUser(WebUserVo vo, String Authorization);

    ResponseResult deleteUser(WebUserVo vo, String Authorization);

    ResponseResult addUser(WebUserVo vo);

    ResponseResult uploadUserImage(MultipartFile file);
}
