package com.fana.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
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

    @Value("${cloudflare.imagePath}")
    private String imagePath;

    @Value("${cloudflare.imageToken}")
    private String imageToken;

    @Value("${cloudflare.imageUrl}")
    private String imageUrl;

    @Value("${cloudflare.videoToken}")
    private String videoToken;

    @Value("${cloudflare.videoUrl}")
    private String videoUrl;

    private HashMap<String, String> pathDev = new HashMap<String, String>() {
        {
            this.put("banner", "C:\\Users\\27466\\Desktop\\fana\\banner\\");
            this.put("achievement", "C:\\Users\\27466\\Desktop\\fana\\achievement\\");
            this.put("charity", "C:\\Users\\27466\\Desktop\\fana\\charity\\");
            this.put("user", "C:\\Users\\27466\\Desktop\\fana\\user\\");
            this.put("leader", "C:\\Users\\27466\\Desktop\\fana\\leader\\");


        }
    };
    private HashMap<String, String> pathTest = new HashMap<String, String>() {
        {
            this.put("banner", "/app/file/images/banner/");
            this.put("achievement", "/app/file/images/achievement/");
            this.put("charity", "/app/file/images/charity/");
            this.put("user", "/app/file/images/user/");
            this.put("leader", "/app/file/images/leader/");


        }
    };

    /**
     * 通过前缀获取文件路径
     *
     * @param prefix
     * @return
     */
    public String getPathActive(String prefix) {
        HashMap<String, String> map = null;
        if ("dev".equals(active)) {
            map = pathDev;
            String s = pathDev.get(prefix);
//            return pathDev.get(prefix);
        } else {
            map = pathTest;
//            return pathTest.get(prefix);
        }
//        if(StrUtil.isBlank(map.get(prefix))){
//            throw new CustomException(200,"success");
//        }
        return map.get(prefix);
    }

    public String upload(MultipartFile file, String path) throws IOException {
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

    /**
     * 上传文件根据前缀上传至指定对应目录
     *
     * @param file
     * @param prefix
     * @return
     * @throws IOException
     */
    public String uploadFile(MultipartFile file, String prefix) throws IOException {
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
        String pathActive = getPathActive(prefix);
        if (StrUtil.isBlank(pathActive)) {
            return "";
        }
        String filePath = pathActive + originalFilename;//   /app/file/images/charity
        log.info(filePath);
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return ip + prefix + "/" + originalFilename;
    }

    /**
     * 删除文件，通过文件url截取路径中前缀删除对应路径中的文件
     *
     * @param fileUrl
     * @return
     */
    public Boolean deleteByFile(String fileUrl) {
        try {
            String s = getPathActive(fileUrl.split("/")[3]);
            if (StrUtil.isBlank(s)) {
                return true;
            }
            if (new File(s + fileUrl.split("/")[4]).exists()) {
                FileSystemUtils.deleteRecursively(new File(s + fileUrl.split("/")[4]));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean deleteByFileName(String filename, String prefix) {
        try {
            if (StrUtil.isBlank(filename)) {
                return true;
            }
            String pathActive = getPathActive(prefix);
            if (StrUtil.isBlank(pathActive)) {
                return true;
            }
            if (new File(pathActive + filename).exists()) {
                FileSystemUtils.deleteRecursively(new File(pathActive + filename));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getFileName(String fileUrl) {
        try {
            if(fileUrl.contains(imagePath)) {
                String s = fileUrl.split("/")[4];
                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
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

    /**
     * 检测 文件是否存在
     *
     * @param filename
     * @param prefix
     * @return
     */
    public boolean isExistFile(String filename, String prefix) {
        try {
            String pathActive = getPathActive(prefix);
            if (new File(pathActive + filename).exists()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("检验图片是否存在异常::error:{}", e.getMessage());
            return false;
        }
    }

    public String cloudflareImage(MultipartFile file) {
        try {
            File file1 = transferToFile(file);
            String body = HttpRequest.post(imageUrl).header(Header.AUTHORIZATION,imageToken)
                    .contentType("multipart/form-data").form("file",file1).execute().body();
            log.info(body);
            /**
             * result : {"id":"83b4a787-ac40-4f6f-eaf0-61d87960c700","filename":"renren (1).png1305780561539279641.png","uploaded":"2022-10-13T08:25:34.737Z","requireSignedURLs":false,"variants":["https://imagedelivery.net/2chPAg1PDekJ6oI478IB9Q/83b4a787-ac40-4f6f-eaf0-61d87960c700/public"]}
             * success : true
             * errors : []
             * messages : []
             */
            JSONObject jsonObject = JSONObject.parseObject(body);
            if(jsonObject.getBoolean("success")){
                String id = jsonObject.getJSONObject("result").getString("id");
                return id;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException(-1,"图片上传失败");
        }
        return null;

    }

    public String cloudflareVideo(MultipartFile file) {
        try {
            File file1 = transferToFile(file);
            String body = HttpRequest.post(videoUrl).header(Header.AUTHORIZATION,videoToken)
                    .contentType("multipart/form-data").form("file",file1).execute().body();
            log.info(body);
            /**
             * result : {"id":"83b4a787-ac40-4f6f-eaf0-61d87960c700","filename":"renren (1).png1305780561539279641.png","uploaded":"2022-10-13T08:25:34.737Z","requireSignedURLs":false,"variants":["https://imagedelivery.net/2chPAg1PDekJ6oI478IB9Q/83b4a787-ac40-4f6f-eaf0-61d87960c700/public"]}
             * success : true
             * errors : []
             * messages : []
             */
            JSONObject jsonObject = JSONObject.parseObject(body);
            if(jsonObject.getBoolean("success")){
                String id = jsonObject.getJSONObject("result").getString("uid");
                return id;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException(-1,"图片上传失败");
        }
        return null;

    }

    public File transferToFile(MultipartFile multipartFile) {
        //选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            //获取文件后缀
            String prefix = originalFilename.substring(originalFilename.lastIndexOf("."));
            file = File.createTempFile(originalFilename, prefix);    //创建零食文件
            multipartFile.transferTo(file);
            //删除
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public boolean deleteImageByCloudFlare(String id){
        try {
            String body = HttpRequest.delete(imageUrl+"/"+id).header(Header.AUTHORIZATION,imageToken)
                    .execute().body();
            log.info(body);
            /**
             * result : {"id":"83b4a787-ac40-4f6f-eaf0-61d87960c700","filename":"renren (1).png1305780561539279641.png","uploaded":"2022-10-13T08:25:34.737Z","requireSignedURLs":false,"variants":["https://imagedelivery.net/2chPAg1PDekJ6oI478IB9Q/83b4a787-ac40-4f6f-eaf0-61d87960c700/public"]}
             * success : true
             * errors : []
             * messages : []
             */
            JSONObject jsonObject = JSONObject.parseObject(body);
            if(jsonObject.getBoolean("success")){

                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException(-1,"图片删除失败");
        }
        return false;
    }

    public static void main(String[] args) {
        String url = "https://imagedelivery.net/2chPAg1PDekJ6oI478IB9Q/87394a64-8242-4b34-2703-d4165b490300/public";
        if(url.contains("https://imagedelivery.net/2chPAg1PDekJ6oI478IB9Q/")) {
            System.out.println(url.split("/")[4]);
        }
    }

}
