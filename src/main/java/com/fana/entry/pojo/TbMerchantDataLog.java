package com.fana.entry.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author astupidcoder
 * @since 2022-08-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TbMerchantDataLog extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("merchant_id")
    private Integer merchantId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 名
     */
    @TableField("first_name")
    private String firstName;

    /**
     * 姓
     */
    @TableField("last_name")
    private String lastName;

    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 消费金额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    private String currency;

    @TableField(value = "create_at", fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private LocalDateTime createAt;

    @TableField(value = "update_at", fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private LocalDateTime updateAt;


}
