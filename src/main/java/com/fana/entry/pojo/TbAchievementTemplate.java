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
 * @since 2022-06-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TbAchievementTemplate extends Model {

    private static final long serialVersionUID = 1L;

    /**
     * 徽章模板表主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 徽章名称
     */
    private String name;

    /**
     * 徽章图片url
     */
    @TableField("achievement_img_url")
    private String achievementImgUrl;

    @TableField(value = "create_at", fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")//序列化
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")//反序列化
    private LocalDateTime createAt;
    @TableField(value = "update_at", fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")//序列化
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")//反序列化
    private LocalDateTime updateAt;


}
