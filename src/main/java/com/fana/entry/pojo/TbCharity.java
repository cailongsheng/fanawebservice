package com.fana.entry.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    /**
     * top 1-6
     */
    private Integer isShow;

    /**
     * 机构图片
     */
    private String imageUrl;

    /**
     * 富文本编辑
     */
    private String means;


}
