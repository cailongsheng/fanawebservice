package com.fana.entry.pojo;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * leader cause & friends projects
 * </p>
 *
 * @author astupidcoder
 * @since 2022-06-02
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_leader_board")
public class TbLeaderBoard extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 活动标题
     */
    @TableField("activity_name")
    private String activityName;

    /**
     * 活动图片路径
     */
    @TableField("activity_image_url")
    private String activityImageUrl;

    /**
     * 活动捐款总额度
     */
    @TableField("donation_amount")
    private BigDecimal donationAmount;

    /**
     * 活动目标金额
     */
    @TableField("donate_goal")
    private BigDecimal donateGoal;

    /**
     * 是否删除
     */
    @TableField("is_delete")
    private int isDelete;
    /**
     * 机构id
     */
    @TableField("charity_id")
    private int charityId;

    @TableField(value = "update_at",fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")//序列化
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")//反序列化
    private LocalDateTime updateAt;

    @TableField(value = "create_at",fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")//序列化
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")//反序列化
    private LocalDateTime createAt;


}
