package com.fana.controller;


import com.fana.config.ResponseResult;
import com.fana.entry.vo.BannerVo;
import com.fana.service.ITbBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/banner")
@Api("轮询图")
public class TbBannerController {
    @Resource
    ITbBannerService bannerService;

    @PostMapping("/save")
    @ApiOperation(value = "web admin add banner")
    public ResponseResult saveBanner(@RequestBody List<BannerVo> banners){
       return bannerService.saveBanner(banners);
    }

    @PostMapping("/list")
    @ApiOperation(value = "web admin banner list")
    public ResponseResult getBannerList(){
        return bannerService.getBannerList();
    }
    @PostMapping("/update")
    @ApiOperation(value = "web admin update banner ")
    public ResponseResult updateBanner(@RequestBody BannerVo vo){
        return bannerService.updateBanner(vo);
    }
    @PostMapping("/delete")
    @ApiOperation(value = "web admin delete banner ")
    public ResponseResult deleteBanner(@RequestBody BannerVo vo){
        return bannerService.deleteBanner(vo);
    }

    @PostMapping("/upload")
    @ApiOperation(value = "web admin upload banner ")
    public ResponseResult uploadBanner(MultipartFile file){
        return bannerService.uploadBanner(file);
    }

}
