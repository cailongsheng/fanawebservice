package com.fana.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fana.entry.pojo.TbCharity;
import com.fana.entry.pojo.TbUser;
import com.fana.entry.vo.*;
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

    List<TbUser> selectUserList(@Param("vo") RechargeRecordVo vo);

    Integer getUserListCount(@Param("vo") AppUserVo vo);

}
