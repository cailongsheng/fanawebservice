package com.fana.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fana.config.ResponseResult;
import com.fana.config.Status;
import com.fana.entry.pojo.TbBanner;
import com.fana.entry.vo.ApiBannerAndCharityVo;
import com.fana.entry.vo.BannerVo;
import com.fana.entry.vo.IPageVo;
import com.fana.exception.CustomException;
import com.fana.mapper.TbBannerMapper;
import com.fana.service.ITbBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fana.utils.FileUtils;
import com.fana.utils.LogUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-05-23
 */
@Service
public class TbBannerServiceImpl extends ServiceImpl<TbBannerMapper, TbBanner> implements ITbBannerService {
    @Resource
    FileUtils fileUtils;
    @Resource
    TbBannerMapper bannerMapper;
    @Value("${fana.ip}")
    private String ip;

    @Override
    public ResponseResult saveBanner(BannerVo banners) {
                bannerMapper.insert(TbBanner.builder()
                        .target(banners.getTarget())
                        .imageName(banners.getImageName())
                        .imagePath(fileUtils.getFileName(banners.getImagePath()))
                        .charityId(banners.getCharityId())
                        .build());
        return new ResponseResult(200, "success");
    }

    @Override
    public ResponseResult getBannerList(BannerVo vo) {
        String banners = ip + "banner/";
        String charity = ip + "charity/";
        long pageNum = vo.getPageNum() == 1l ? 0 : (vo.getPageNum() - 1) * vo.getPageSize();
        List<ApiBannerAndCharityVo> bannerAndCharty = bannerMapper.getBannerAndCharity(vo,pageNum,vo.getPageSize());
        bannerAndCharty.forEach(banner->{
            banner.setImagePath(banners+banner.getImagePath());
            banner.setImageUrl(charity+banner.getImageUrl());
        });
        IPageVo iPageVo = IPageVo.builder()
                .total(bannerMapper.selectCount(new QueryWrapper<>()))
                .pageSize(vo.getPageSize())
                .pageNum(vo.getPageNum())
                .list(bannerAndCharty)
                .build();
        return ResponseResult.success(iPageVo);
    }

    @Override
    public ResponseResult updateBanner(BannerVo vo) {
        String uploadPath = null;
        try {
            boolean existFile = fileUtils.isExistFile(fileUtils.getFileName(vo.getImagePath()), "banner");
            if (!existFile && ObjectUtil.isNotEmpty(vo.getFile()))
                uploadPath = fileUtils.uploadFile(vo.getFile(), "banner");
            else
                uploadPath = vo.getImagePath();
        } catch (IOException e) {
            LogUtil.addErrorLog("上传图片文件异常", "/banner/update", ip, e.getMessage().substring(0, 200));
            throw new CustomException(201, "File upload failed.");
        }

        try {
            bannerMapper.updateById(TbBanner.builder()
                    .id(vo.getId()).imageName(vo.getImageName())
                    .imagePath(fileUtils.getFileName(uploadPath))
                    .target(vo.getTarget())
                    .charityId(vo.getCharityId())
                    .build());
        } catch (Exception e) {
            LogUtil.addErrorLog("保存banner异常", "/banner/update/id", ip, e.getMessage().substring(0, 200));
            throw new CustomException(201, "banner update failed.");
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult deleteBanner(BannerVo vo) {
        int byId = bannerMapper.deleteById(vo.getId());
        if (byId!=0) return ResponseResult.success();
        return ResponseResult.error(byId);
    }

    @Override
    public ResponseResult uploadBanner(MultipartFile file) {
        LogUtil.addInfoLog("banner图片上传", "/banner/upload", null);
        if (file == null) {
            return new ResponseResult(Status.PARAMETER_ERROR.code, "The file did not fill in  ");
        }
        String upload = null;
        try {
            upload = fileUtils.uploadFile(file, "banner");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(upload)) {
            throw new CustomException(Status.IMAGE_UPLOAD_FAILED.getCode(), Status.IMAGE_UPLOAD_FAILED.getMessage());
        }
        return ResponseResult.success(upload);
    }


}
