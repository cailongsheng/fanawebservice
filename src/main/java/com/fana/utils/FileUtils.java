package com.fana.utils;

import cn.hutool.core.lang.UUID;
import com.fana.config.Status;
import com.fana.exception.CustomException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FileUtils {

    @Value("${fana.ip}")
    private String ip;

    @Value("${fana.banner.imgPath}")
    private String imgPath;




    public String upload(MultipartFile file) throws IOException {
        if (file == null) {
            return null;
        }
        //获取InputStream
        InputStream in = file.getInputStream();
        //根据文件头获取文件类型
        String type = FileType.getFileType(in);
        if (StringUtils.isEmpty(type)) {
            throw new CustomException(Status.FILE_TYPE_ERROR.code, Status.FILE_TYPE_ERROR.message);
        }
        String fileName = file.getOriginalFilename();
        String fileTyle = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        String originalFilename = UUID.randomUUID().toString() + fileTyle;
        String filePath = imgPath + originalFilename;//   /app/file/images/charity
        System.out.println(filePath);
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return ip + "charity" + originalFilename;
    }


    public String uploadAutograph(MultipartFile file) throws IOException {
        if (file == null) {
            return null;
        }
        //获取InputStream
        InputStream in = file.getInputStream();
        //根据文件头获取文件类型
        String type = FileType.getFileType(in);
        if (StringUtils.isEmpty(type)) {
            throw new CustomException(Status.FILE_TYPE_ERROR.code, Status.FILE_TYPE_ERROR.message);
        }
        String fileName = file.getOriginalFilename();
        String fileTyle = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        String originalFilename = UUID.randomUUID().toString() + fileTyle;
        String filePath = imgPath + originalFilename;
        System.out.println(filePath);
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return ip + "autograph/" + originalFilename;
    }



}
