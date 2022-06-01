package com.fana.entry.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class GetCharityListVo implements Serializable {
    private Integer id;

    private Integer categoryId;

    private String charityName;

    private String categoryName;

    private String website;

    private String description;

    private Integer isShow;

    private String imageUrl;

    private String means;
}
