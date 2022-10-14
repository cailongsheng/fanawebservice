package com.fana.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fana.config.ResponseResult;
import com.fana.config.Status;
import com.fana.entry.vo.UploadFileVo;
import com.fana.exception.CustomException;
import com.fana.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/file")
@CrossOrigin
public class FilesController {
    @Resource
    private FileUtils fileUtils;
    @Value("${spring.profiles.active}")
    private String active;

    @Value("${cloudflare.imagePath}")
    private String imagePath;

    @PostMapping("/uploadImage")
    public ResponseResult upload(MultipartFile file) {
        if(file == null){
            return new ResponseResult(Status.PARAMETER_ERROR.code, "The file did not fill in  ");
        }

        String upload = null;
        try {
            upload = fileUtils.cloudflareImage(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(StrUtil.isBlank(upload)){
            throw new CustomException(Status.IMAGE_UPLOAD_FAILED.getCode(),Status.IMAGE_UPLOAD_FAILED.getMessage());
        }
        return ResponseResult.success(imagePath+upload+"/public");

    }

    @PostMapping("/deleteImage")
    public ResponseResult deleteImage(String id) {
        if(id == null){
            return new ResponseResult(Status.PARAMETER_ERROR.code, "The id did not fill in  ");
        }
        return ResponseResult.success(fileUtils.deleteImageByCloudFlare(id));

    }


    @PostMapping("/uploadVideo")
    public ResponseResult delete(MultipartFile file){
        if(file == null){
            return new ResponseResult(Status.PARAMETER_ERROR.code, "The file did not fill in  ");
        }

        String upload = null;
        try {
            upload = fileUtils.cloudflareVideo(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(StrUtil.isBlank(upload)){
            throw new CustomException(Status.IMAGE_UPLOAD_FAILED.getCode(),Status.IMAGE_UPLOAD_FAILED.getMessage());
        }
        return ResponseResult.success("https://customer-3wzcy4n8k3nuefkr.cloudflarestream.com/"+upload+"/iframe?poster=https%3A%2F%2Fcustomer-3wzcy4n8k3nuefkr.cloudflarestream.com%2Fc"+upload+"%2Fthumbnails%2Fthumbnail.jpg%3Ftime%3D%26height%3D600");
    }


}
