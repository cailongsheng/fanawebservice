package com.fana.entry.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannerVo {
    private Integer id;

    private String imageName;

    private String imagePath;

    private String target;

    private MultipartFile file;
}
