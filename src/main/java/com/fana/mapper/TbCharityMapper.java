package com.fana.mapper;

import com.fana.entry.pojo.TbCharity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fana.entry.vo.AppUserListVo;
import com.fana.entry.vo.AppUserVo;
import com.fana.entry.vo.CharityVo;
import com.fana.entry.vo.GetCharityListVo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author astupidcoder
 * @since 2022-05-31
 */
public interface TbCharityMapper extends BaseMapper<TbCharity> {

    List<GetCharityListVo> getCharityList(@Param("vo") CharityVo vo);

    Integer getCharityListCount(@Param("vo") CharityVo vo);

    List<AppUserListVo> getUserList(@Param("vo") AppUserVo vo);

    Integer getUserListCount(@Param("vo") AppUserVo vo);

}
