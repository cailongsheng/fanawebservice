package com.fana.entry.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fana.entry.pojo.TbLeaderBoard;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderBoardListVo implements Serializable {
    private Integer id;

    private String activityName;

    private String activityImageUrl;


    private BigDecimal donationAmount;


    private BigDecimal donateGoal;


    private int isDelete;

    private int charityId;

    private LocalDateTime updateAt;

    private LocalDateTime createAt;

    private Integer category;

    private String charityName;

    private String imageUrl;

    private String categoryName;
}
