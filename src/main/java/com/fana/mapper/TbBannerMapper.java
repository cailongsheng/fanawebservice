package com.fana.mapper;

import com.fana.entry.pojo.TbBanner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fana.entry.vo.ApiBannerAndCharityVo;
import com.fana.entry.vo.BannerVo;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author astupidcoder
 * @since 2022-05-23
 */
public interface TbBannerMapper extends BaseMapper<TbBanner> {

    @Select("SELECT\n" +
            "  tbb.image_name  AS imageName,\n" +
            "  tbb.id, \n" +
            "  tbb.image_path  AS imagePath,\n" +
            "  tbb.create_at   AS createAt,\n" +
            "  tbb.update_at   AS updateAt,\n" +
            "  tbb.charity_id   AS charityId,\n" +
            "  tbb.target,\n" +
            "  tbc.charity,\n" +
            "  tbc.website,\n" +
            "  tbc.description,\n" +
            "  tbc.image_url   AS imageUrl\n" +
            "FROM tb_banner tbb\n" +
            "  LEFT JOIN tb_charity tbc\n" +
            "    ON tbb.charity_id = tbc.id\n" +
            "    LIMIT #{pageNum},#{pageSize}")
    List<ApiBannerAndCharityVo> getBannerAndCharity(@Param("vo") BannerVo vo,@Param("pageNum") long pageNum,@Param("pageSize") long pageSize);

}
