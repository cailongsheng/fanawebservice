package com.fana.service;

import com.fana.config.ResponseResult;
import com.fana.entry.pojo.TbBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fana.entry.vo.BannerVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-05-23
 */
public interface ITbBannerService extends IService<TbBanner> {

    ResponseResult uploadBanner(List<BannerVo> banners);

    ResponseResult downLoadBanner();

}
