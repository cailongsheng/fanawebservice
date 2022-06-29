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
 *
 * </p>
 *
 * @author astupidcoder
 * @since 2022-06-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_user_money")
public class TbUserMoney extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 关联机构id
     */
    @TableField(value = "charity_id")
    private Integer charityId;

    /**
     * 用户邮箱
     */
    @TableField(value = "username")
    private String username;

    /**
     * 用户名id
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 捐款金额
     */
    @TableField(value = "money")
    private BigDecimal money;

    @TableField(value = "charity_name")
    private String charityName;

    @TableField(value = "update_at", fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")//序列化
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")//反序列化
    private LocalDateTime updateAt;

    @TableField(value = "create_at", fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")//序列化
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")//反序列化
    private LocalDateTime createAt;

    /**
     * 删除
     */
    @TableField(value = "is_delete")
    private int isDelete;


}
