package com.fana.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fana.config.ResponseResult;
import com.fana.config.Status;
import com.fana.entry.pojo.TbBanner;
import com.fana.entry.vo.BannerVo;
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
    public ResponseResult saveBanner(List<BannerVo> banners) {
        banners.forEach(img -> {
            try {
                bannerMapper.insert(TbBanner.builder()
                        .target(img.getTarget())
                        .imageName(img.getImageName())
                        .imagePath(fileUtils.getFileName(img.getImagePath())).build());
            } catch (Exception e) {
                LogUtil.addErrorLog("创建banner异常", "/banner/save", ip, e.getMessage().substring(0, 200));
                throw new CustomException(201, "File save failed.");
            }

        });
        return new ResponseResult(200, "success");
    }

    @Override
    public ResponseResult getBannerList() {
        String path = ip + "banner/";
        List<TbBanner> tbBannerList = bannerMapper.selectList(new QueryWrapper<>());
        if (!tbBannerList.isEmpty())
            tbBannerList.forEach(banner -> {
                banner.setImagePath(path + banner.getImagePath());
            });
        return ResponseResult.success(tbBannerList);
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
            bannerMapper.updateById(TbBanner.builder().id(vo.getId()).imageName(vo.getImageName()).imagePath(fileUtils.getFileName(uploadPath)).target(vo.getTarget()).build());
        } catch (Exception e) {
            LogUtil.addErrorLog("保存banner异常", "/banner/update/id", ip, e.getMessage().substring(0, 200));
            throw new CustomException(201, "banner update failed.");
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult deleteBanner(BannerVo vo) {
        return null;
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
