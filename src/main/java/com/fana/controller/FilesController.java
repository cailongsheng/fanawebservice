package com.fana.controller;

import com.fana.entry.vo.UploadFileVo;
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



    @PostMapping("upload")
    private String upload(UploadFileVo vo) throws IOException {
        return fileUtils.uploadFile(vo.getFile(),vo.getPrefix());

    }

    @PostMapping("delete")
    private Boolean delete(String fileUrl){
        return fileUtils.deleteByFile(fileUrl);
    }


}
