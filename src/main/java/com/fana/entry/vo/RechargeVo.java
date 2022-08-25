package com.fana.entry.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RechargeVo implements Serializable {

    private int amount;

    /**
     * 币种
     */
    private String currency;

    private Integer userId;
}
