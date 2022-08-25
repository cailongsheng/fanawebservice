package com.fana.mapper;

import com.fana.entry.pojo.TbUserMoney;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fana.entry.vo.ApiUserMoneyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author astupidcoder
 * @since 2022-06-23
 */
@Mapper
public interface TbUserMoneyMapper  extends BaseMapper<TbUserMoney> {
    List<ApiUserMoneyVo> getUserMoneyList(@Param("vo")ApiUserMoneyVo vo);

    Integer getUserMoneyCount(@Param("vo") ApiUserMoneyVo money);
}
