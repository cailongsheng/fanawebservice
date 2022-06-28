package com.fana.entry.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
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
public class AchievementTemplateVo implements Serializable {
    @ApiModelProperty("模板id")
    private Integer id;

    @ApiModelProperty("徽章名")
    private String name;

    @ApiModelProperty("徽章logo")
    private String url;
}
