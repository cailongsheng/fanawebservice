package com.fana.controller;

import com.fana.utils.FileUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/file")
@CrossOrigin
public class FilesController {
    @Resource
    private FileUtils fileUtils;

    @PostMapping("upload")
    private String upload(MultipartFile file) throws IOException {
        return fileUtils.uploadFile(file,"charity");
    }

    @PostMapping("delete")
    private Boolean delete(String fileUrl){
        return fileUtils.deleteByFile(fileUrl);
    }


}
