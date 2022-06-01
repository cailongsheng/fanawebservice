package com.fana.entry.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author astupidcoder
 * @since 2022-05-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TbCharity extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类别
     */
    private String classs;

    /**
     * 机构信息
     */
    private String charity;

    /**
     * 机构平台连接
     */
    private String website;

    /**
     * 机构描述
     */
    private String description;

    @TableField(value = "create_at", fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")//序列化
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")//反序列化
    private LocalDateTime createAt;
    @TableField(value = "update_at", fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")//序列化
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")//反序列化
    private LocalDateTime updateAt;

    /**
     * top 1-6
     */
    @TableField("is_show")
    private Integer isShow;

    /**
     * 机构图片
     */
    @TableField("image_url")
    private String imageUrl;

    /**
     * 富文本编辑
     */
    private String means;


}
