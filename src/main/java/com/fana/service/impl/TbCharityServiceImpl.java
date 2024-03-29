package com.fana.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fana.config.ResponseResult;
import com.fana.config.Status;
import com.fana.entry.pojo.TbCharity;
import com.fana.entry.pojo.TbClass;
import com.fana.entry.pojo.TbUserCharity;
import com.fana.entry.vo.CharityVo;
import com.fana.entry.vo.GetCharityListVo;
import com.fana.entry.vo.IPageVo;
import com.fana.entry.vo.UpdateSortVo;
import com.fana.exception.CustomException;
import com.fana.mapper.TbCharityMapper;
import com.fana.mapper.TbClassMapper;
import com.fana.mapper.TbUserCharityMapper;
import com.fana.service.ITbCharityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fana.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
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
    @Value("${fana.ip}")
    private String fanaIp;

    @Override
    public ResponseResult getCharityCategory() {
        List<TbClass> tbClasses = classMapper.selectList(null);
        return ResponseResult.success(tbClasses);
    }

    /**
     * 修改:2022年7月21日11:54:05
     * 内容: GetCharityListVo 添加新字段 imageUrlBack  机构海报图
     * by: wwm
     * @param vo
     * @return
     */
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
        list.stream().forEach(a->{
            if(StrUtil.isNotBlank(a.getImageUrl())){
                a.setImageUrl(fanaIp+"charity/"+a.getImageUrl());
                a.setImageUrlBack(fanaIp+"charity/"+a.getImageUrlBack());
            }
            try {
                a.setDescription( new String(a.getDescription().getBytes("ISO-8859-1"),"utf-8") );
            } catch (UnsupportedEncodingException e) {
               log.error("字符串编码解析错误;{}",e);
            }
        });
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
        charity.setImageUrl(fileUtils.getFileName(vo.getImageUrl()));
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
//        fileUtils.deleteByFileName(charity.getImageUrl(),"charity");
        charity.setImageUrl(fileUtils.getFileName(vo.getImageUrl()));
        charity.setImageUrlBack(fileUtils.getFileName(vo.getImageUrlBack()));
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
//        fileUtils.deleteByFileName(charity.getImageUrl(),"charity");
        charityMapper.deleteById(vo.getId());
        userCharityMapper.delete(new QueryWrapper<TbUserCharity>().lambda().eq(TbUserCharity::getCharityId,charity.getId()));
        return ResponseResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateSort(List<UpdateSortVo> vo) {
        vo.forEach(a ->{
            if(ObjectUtil.isEmpty(a.getId())){
                throw new CustomException(Status.PARAMETER_ERROR.code,"The id did not fill in");
            }
            TbCharity charity = new TbCharity();
//            charity.setId(a.getId());
//            charity.setSortId(a.getSortId());

            charityMapper.update(charity,new UpdateWrapper<TbCharity>().set("sort_id",a.getSortId()).eq("id",a.getId()));
        });
        return  ResponseResult.success("success");
    }


}
