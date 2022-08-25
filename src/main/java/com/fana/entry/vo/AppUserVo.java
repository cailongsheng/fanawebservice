package com.fana.entry.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
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
@ApiModel("app user entry")
public class AppUserVo {
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
    private Integer platform;//平台
    private String search; //条件
    private String email;//APP 账号
    private String firstName;//APP 名
    private String lastName;//APP 姓
    private String birthday;
    private Integer sex;//0:女 1:男
    private String avator;//头像
    private Integer isDelete;//头像
    @ApiModelProperty(value = "查询登入来源 0all，1apple，2google")
    private Integer type;
}
