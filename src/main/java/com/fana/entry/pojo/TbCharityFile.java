package com.fana.entry.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author astupidcoder
 * @since 2022-06-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TbCharityFile extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("charity_id")
    private Integer charityId;

    @TableField("file_name")
    private String fileName;

    @TableField("file_url")
    private String fileUrl;


}
