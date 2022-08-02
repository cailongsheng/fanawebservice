package com.fana.entry.pojo;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author astupidcoder
 * @since 2022-08-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbOrder extends Model {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 金额
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;

    /**
     * 金额
     */
    @TableField(exist = false)
    private BigDecimal money;

    /**
     * 币种
     */
    private String currency;

    /**
     * 机构id
     */
    @TableField("charity_id")
    private Integer charityId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 支付意向key
     */
    @TableField("client_secret")
    private String clientSecret;

    /**
     * 订单支付状态  NOTPAY 初始化  SUCCESS 支付成功 PAYING 支付中 CANCEL已取消 TIMEOUT 超时 FAILURE失败
     */
    @TableField("pay_status")
    private String payStatus;

    /**
     * RECHARGE 充值钱包 WALLETPAY 钱包支付 MOBILEPAY 手机支付  WEB_RECHARGE admin充值
     */
    @TableField("type")
    private String type;

    /**
     * 订单通知状态  NOTNOTICES 未通知  NOTICESUCCESS 通知成功 NOTICEFAIL 通知失败
     */
    @TableField("web_hook_Status")
    private String webHookStatus;

    @TableField("submit_time")
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")//序列化
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")//反序列化
    private LocalDateTime submitTime;

    @TableField(value = "create_at",fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")//序列化
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM")//反序列化
    private LocalDateTime createAt;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")//序列化
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM")//反序列化
    @TableField(value = "update_at",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateAt;



}
