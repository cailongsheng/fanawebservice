package com.fana.entry.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
@Data
public class UploadFileVo implements Serializable {
    private MultipartFile file;
    /**
     * 前缀，不同的前缀文件上传的目录不同
     */
    private String prefix;
}
