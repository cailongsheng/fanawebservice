package com.fana.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fana.config.ResponseResult;
import com.fana.config.Status;
import com.fana.entry.pojo.TbCharity;
import com.fana.entry.pojo.TbClass;
import com.fana.entry.pojo.TbUserCharity;
import com.fana.entry.vo.CharityVo;
import com.fana.entry.vo.GetCharityListVo;
import com.fana.entry.vo.IPageVo;
import com.fana.exception.CustomException;
import com.fana.mapper.TbCharityMapper;
import com.fana.mapper.TbClassMapper;
import com.fana.mapper.TbUserCharityMapper;
import com.fana.service.ITbCharityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fana.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-05-31
 */
@Service
public class TbCharityServiceImpl extends ServiceImpl<TbCharityMapper, TbCharity> implements ITbCharityService {
    @Resource
    private TbClassMapper classMapper;

    @Resource
    private TbCharityMapper charityMapper;

    @Resource
    private TbUserCharityMapper userCharityMapper;
    @Resource
    private FileUtils fileUtils;

    @Override
    public ResponseResult getCharityCategory() {
        List<TbClass> tbClasses = classMapper.selectList(null);
        return ResponseResult.success(tbClasses);
    }

    @Override
    public ResponseResult getCharityList(CharityVo vo) {
        IPageVo iPageVo = new IPageVo();
        iPageVo.setPageNum(vo.getPageNum());
        iPageVo.setPageSize(vo.getPageSize());
        //page
        if (vo.getPageNum() == 1||vo.getPageNum() ==0) {
            vo.setPageNum(0l);
        } else {
            vo.setPageNum((vo.getPageNum() - 1) * vo.getPageSize());
        }
        List<GetCharityListVo> list = charityMapper.getCharityList(vo);
        Integer charityListCount = charityMapper.getCharityListCount(vo);
        iPageVo.setTotal(charityListCount.longValue());
        iPageVo.setList(list);
        return ResponseResult.success(iPageVo);
    }

    @Override
    public ResponseResult addCharity(GetCharityListVo vo) {
        TbCharity charity = new TbCharity();
        charity.setCharity(vo.getCharityName());
        charity.setClasss(vo.getCategoryId().toString());
        charity.setCreateAt(LocalDateTime.now());
        charity.setUpdateAt(LocalDateTime.now());
        charity.setDescription(vo.getDescription());
        charity.setImageUrl(vo.getImageUrl());
        charity.setIsShow(vo.getIsShow());
        charity.setWebsite(vo.getWebsite());
        if(StrUtil.isBlank(vo.getMeans())){
            vo.setMeans("");
        }else{
            charity.setMeans(vo.getMeans());
        }
        int insert = charityMapper.insert(charity);
        return ResponseResult.success(insert);
    }

    @Override
    public ResponseResult updateCharity(GetCharityListVo vo) {
        TbCharity charity = charityMapper.selectById(vo.getId());
        if(ObjectUtil.isEmpty(charity)){
            throw new CustomException(Status.CHARITY_NOT_EXIXT.code,Status.CHARITY_NOT_EXIXT.message);
        }
        charity.setCharity(vo.getCharityName());
        charity.setClasss(vo.getCategoryId().toString());
        charity.setCreateAt(LocalDateTime.now());
        charity.setUpdateAt(LocalDateTime.now());
        charity.setDescription(vo.getDescription());
        fileUtils.deleteByFile(charity.getImageUrl());
        charity.setImageUrl(vo.getImageUrl());
        charity.setIsShow(vo.getIsShow());
        charity.setWebsite(vo.getWebsite());
        if(StrUtil.isBlank(vo.getMeans())){
            vo.setMeans("");
        }else{
            charity.setMeans(vo.getMeans());
        }
        charityMapper.updateById(charity);
        return ResponseResult.success(charity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteCharity(GetCharityListVo vo) {
        TbCharity charity = charityMapper.selectById(vo.getId());
        if(ObjectUtil.isEmpty(charity)){
            throw new CustomException(Status.CHARITY_NOT_EXIXT.code,Status.CHARITY_NOT_EXIXT.message);
        }
        fileUtils.deleteByFile(charity.getImageUrl());
        charityMapper.deleteById(vo.getId());
        userCharityMapper.delete(new QueryWrapper<TbUserCharity>().lambda().eq(TbUserCharity::getCharityId,charity.getId()));
        return ResponseResult.success();
    }


}
