package com.fana.service;

import com.fana.config.ResponseResult;
import com.fana.entry.pojo.TbCharity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fana.entry.vo.CharityVo;
import com.fana.entry.vo.GetCharityListVo;
import com.fana.entry.vo.UpdateSortVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-05-31
 */
public interface ITbCharityService extends IService<TbCharity> {

    ResponseResult getCharityCategory();

    ResponseResult getCharityList(CharityVo vo);

    ResponseResult addCharity(GetCharityListVo vo);

    ResponseResult updateCharity(GetCharityListVo vo);

    ResponseResult deleteCharity(GetCharityListVo vo);

    ResponseResult updateSort(List<UpdateSortVo> updateSortVos);
}
