package com.fana.entry.vo;

import com.fana.entry.pojo.TbMerchant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Program: fanawebservice
 * @Author: Alex
 * @Description: MerchantVo
 * @Create: 08/03/2022 17:01
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MerchantVO extends TbMerchant implements Serializable {

    private static final long serialVersionUID = -5782565803651927607L;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private Long pageNum;

    /**
     * 每页大小
     */
    @ApiModelProperty(value = "每页大小")
    private Long pageSize;

}
