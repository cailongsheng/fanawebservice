package com.fana.entry.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("leader board entry")
public class LeaderBoardVo {
    private Long pageNum;
    private Long pageSize;
    /**
     * id
     */
    private int id;
    /**
     * 活动标题
     */
    private String activityName;

    /**
     * 活动图片路径
     */
    private String activityImageUrl;

    /**
     * 活动捐款总额度
     */
    private BigDecimal donationAmount;

    /**
     * 活动目标金额
     */
    private BigDecimal donateGoal;

    /**
     * 删除
     */
    private Integer isDelete;

    /**
     * 机构id
     */
    private Integer charityId;

    /**
     *  type  0: leader cause 1: friends project
     */
    private Integer type;

}
