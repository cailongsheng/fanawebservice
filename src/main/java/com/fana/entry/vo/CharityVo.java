package com.fana.entry.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("charity model")
public class CharityVo implements Serializable {
    @ApiModelProperty(value = "当前页")
    private Long pageNum;
    @ApiModelProperty(value = "每页大小")
    private Long pageSize;

    private String charityName;
    private Integer categoryId;
    private String fileUrl;
    private String isShow;


}
