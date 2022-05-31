package com.fana.utils;

import cn.hutool.core.lang.UUID;
import com.fana.config.Status;
import com.fana.exception.CustomException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@Component
@Log4j2
public class FileUtils {

    @Value("${fana.ip}")
    private String ip;

    @Value("${fana.banner.imgPath}")
    private String imgPath;
    @Value("${spring.profiles.active}")
    private String active;

    private HashMap<String,String> pathDev = new HashMap<String, String>(){
        {
            this.put("banner", "C:\\Users\\27466\\Desktop\\fana\\banner\\");
            this.put("achievement", "C:\\Users\\27466\\Desktop\\fana\\achievement\\");
            this.put("charity", "C:\\Users\\27466\\Desktop\\fana\\charity\\");
            System.out.println("dsfegeg");

        }
    };
    private HashMap<String,String> pathTest = new HashMap<String, String>(){
        {
            this.put("banner", "/app/file/images/banner/");
            this.put("achievement", "/app/file/images/achievement/");
            this.put("charity", "/app/file/images/charity/");
        }
    };

    public String getPathActive(String prefix){
        if("dev".equals(active)){
            return pathDev.get(prefix);
        }else{
            return pathTest.get(prefix);
        }
    }

    public String upload(MultipartFile file,String path) throws IOException {
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
        return ip + path + originalFilename;
    }
    public String uploadFile(MultipartFile file,String prefix) throws IOException {
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
        String filePath = getPathActive(prefix) + originalFilename;//   /app/file/images/charity
        log.info(filePath);
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return ip + prefix+"/"+ originalFilename;
    }

    public Boolean deleteByFile(String fileUrl){
        try {
            String s = getPathActive(fileUrl.split("/")[3]);
            if (new File(s + fileUrl.split("/")[4]).exists()) {
                FileSystemUtils.deleteRecursively(new File(s + fileUrl.split("/")[4]));
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
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
