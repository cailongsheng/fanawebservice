package com.fana.entry.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebUserVo {
    private Integer id;
    /**
     * 邮箱
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 角色
     */
    private int roleId;
    @ApiModelProperty(value = "当前页")
    private Long pageNum;
    @ApiModelProperty(value = "每页大小")
    private Long pageSize;
}
