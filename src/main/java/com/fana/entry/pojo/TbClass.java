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
public class TbClass extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 机构类型
     */
    private String classType;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;


}
