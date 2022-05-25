package com.fana.controller;


import com.fana.config.ResponseResult;
import com.fana.entry.vo.BannerVo;
import com.fana.service.ITbBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2022-05-23
 */
@RestController
@RequestMapping("/tb-banner")
public class TbBannerController {
    @Resource
    ITbBannerService bannerService;

    @PostMapping("/web/banner/redact")
    @ApiOperation(value = "web admin banner 编辑")
    public ResponseResult uploadBanner(@RequestBody List<BannerVo> banners){
       return bannerService.uploadBanner(banners);
    }

    @GetMapping("/app/banner/down")
    @ApiOperation(value = "app down load banner image")
    public ResponseResult downLoadBanner(){
        return bannerService.downLoadBanner();
    }

}
