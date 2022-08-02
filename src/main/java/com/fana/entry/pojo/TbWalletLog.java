package com.fana.entry.pojo;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;

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
public class TbWalletLog extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("order_no")
    private String orderNo;

    @TableField("user_id")
    private Integer userId;

    @TableField("total_amount")
    private BigDecimal totalAmount;

    private BigDecimal balance;

    private String remark;

    /**
     * SUCCESS 支付成功 PAYING 支付中 CANCEL已取消 TIMEOUT 超时
     */
    @TableField("pay_status")
    private String payStatus;

    @TableField(value = "create_at",fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")//序列化
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")//反序列化
    private LocalDateTime createAt;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")//序列化
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")//反序列化
    @TableField(value = "update_at",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateAt;

}
