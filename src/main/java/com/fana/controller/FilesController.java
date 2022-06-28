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



    @PostMapping("/upload")
    public ResponseResult upload(UploadFileVo vo) {
        if(vo.getFile() == null){
            return new ResponseResult(Status.PARAMETER_ERROR.code, "The file did not fill in  ");
        }
        if(StrUtil.isBlank(vo.getPrefix())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The prefix did not fill in  ");

        String upload = null;
        try {
            upload = fileUtils.uploadFile(vo.getFile(),vo.getPrefix());

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(StrUtil.isBlank(upload)){
            throw new CustomException(Status.IMAGE_UPLOAD_FAILED.getCode(),Status.IMAGE_UPLOAD_FAILED.getMessage());
        }
        return ResponseResult.success(upload);

    }

    @PostMapping("/delete")
    public Boolean delete(String fileUrl){
        return fileUtils.deleteByFile(fileUrl);
    }


}
