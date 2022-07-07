package com.fana.entry.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserListVo implements Serializable {
    private Integer id;

    /**
     * 邮箱
     */
    private String email;

//    /**
//     * 密码
//     */
//    private String password;

    /**
     * 姓
     */
    private String firstName;

    /**
     * 名
     */
    private String lastName;
    private String birthday;

    /**
     * 性别(1 男，0 女)
     */
    private Integer sex;

    private int platform =1;

    /**
     * 头像地址
     */
    private String avator;

    private String type;

    private Integer isDelete;

    private List<Integer> typeList;
}
