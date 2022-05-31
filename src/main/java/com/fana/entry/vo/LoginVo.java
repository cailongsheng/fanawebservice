package com.fana.entry.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginVo implements Serializable {
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "邮箱")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "角色")
    private int roles;
    @ApiModelProperty(value = "邮箱")
    private String email;
    private Integer userId;

    private String firstName;

    private String lastName;

    private String birthday;
    private String token;
}
