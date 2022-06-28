package com.fana.entry.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BannerVo {
    private Integer id;

    private String imageName;

    private String imagePath;

    private String target;

    private String charityId;

    private MultipartFile file;

    private Long pageSize;
    private Long pageNum;
}
