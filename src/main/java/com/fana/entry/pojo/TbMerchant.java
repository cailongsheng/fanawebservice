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
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TbMerchant extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商户名
     */
    @TableField("merchant_name")
    private String merchantName;

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
     * 联系电话
     */
    private String mobile;

    /**
     * 商家网址
     */
    private String website;

    /**
     * key
     */
    @TableField("`key`")
    private String key;

    /**
     * 商户类型 (0,平台 1,散户)
     */
    private Integer type;

    /**
     * 返利比率
     */
    @TableField("rebate_rate")
    private Integer rebateRate;

    /**
     * 是否删除 0-未删除 1-已删除
     */
    @TableField("is_delete")
    private Integer isDelete;

    @TableField(value = "create_at", fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime createAt;

    @TableField(value = "update_at", fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime updateAt;


}
