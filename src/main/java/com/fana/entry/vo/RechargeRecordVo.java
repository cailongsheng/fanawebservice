package com.fana.entry.vo;

import com.fana.entry.pojo.TbWalletLog;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Program: fanawebservice
 * @Author: Alex
 * @Description: RechargeRecordVo
 * @Create: 08/03/2022 17:01
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RechargeRecordVo extends TbWalletLog {

    /**
     * 名
     */
    @ApiModelProperty(value = "名")
    private String firstName;

    /**
     * 姓
     */
    @ApiModelProperty(value = "姓")
    private String lastName;

    /**
     * 用戶名
     */
    @ApiModelProperty(value = "用戶名")
    private String userName;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private Long pageNum = 1L;

    /**
     * 每页大小
     */
    @ApiModelProperty(value = "每页大小")
    private Long pageSize = 10L;

}
