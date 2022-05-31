package com.fana.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fana.config.ResponseResult;
import com.fana.entry.pojo.TbBanner;
import com.fana.entry.vo.BannerVo;
import com.fana.exception.CustomException;
import com.fana.mapper.TbBannerMapper;
import com.fana.service.ITbBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fana.utils.FileUtils;
import com.fana.utils.LogUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务实现类
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

    @Override
    public ResponseResult uploadBanner(List<BannerVo> banners) {
        banners.forEach(img->{
            try {
                //上传
                String uploadPath = fileUtils.upload(img.getFile(),"banner");
                bannerMapper.insert(TbBanner.builder().imageName(img.getImageName()).imagePath(uploadPath).build());
            } catch (IOException e) {
                LogUtil.addErrorLog("上传图片文件异常", "upload/insert", "ip", e.getMessage().substring(0, 200));
                throw  new CustomException(201, "File upload failed.");
            }

        });
        return new ResponseResult(200,"success");
    }

    @Override
    public ResponseResult downLoadBanner() {
        return ResponseResult.success(bannerMapper.selectList(new QueryWrapper<>()));
    }
}
