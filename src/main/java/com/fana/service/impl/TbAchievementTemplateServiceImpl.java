package com.fana.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fana.config.ResponseResult;
import com.fana.config.Status;
import com.fana.entry.pojo.TbAchievement;
import com.fana.entry.pojo.TbAchievementTemplate;
import com.fana.entry.vo.AchievementTemplateVo;
import com.fana.exception.CustomException;
import com.fana.mapper.TbAchievementMapper;
import com.fana.mapper.TbAchievementTemplateMapper;
import com.fana.service.ITbAchievementTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fana.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-06-08
 */
@Service
public class TbAchievementTemplateServiceImpl extends ServiceImpl<TbAchievementTemplateMapper, TbAchievementTemplate> implements ITbAchievementTemplateService {

    @Value("${fana.ip}")
    private String fanaIp;

    @Resource
    private FileUtils fileUtils;

    @Resource
    private TbAchievementTemplateMapper templateMapper;

    @Resource
    private TbAchievementMapper achievementMapper;

    @Value("${cloudflare.imagePath}")
    private String imagePath;
    @Override
    public ResponseResult getAchievementTemplate() {
        List<TbAchievementTemplate> list = templateMapper.selectList(null);
        list.stream().forEach(a->{
            if(StrUtil.isNotBlank(a.getAchievementImgUrl())){
                a.setAchievementImgUrl(imagePath+a.getAchievementImgUrl()+"/public");
            }
        });
        return ResponseResult.success(list);
    }

    @Override
    public ResponseResult addAchievementTemplate(AchievementTemplateVo vo) {
        List<TbAchievementTemplate> tbAchievementTemplates = templateMapper.selectList(new QueryWrapper<TbAchievementTemplate>().lambda()
                .eq(TbAchievementTemplate::getName, vo.getName()));
        if(CollectionUtil.isNotEmpty(tbAchievementTemplates)) throw new CustomException(Status.ACHIEVEMENT_TEMPLATE_ERROR.code,Status.ACHIEVEMENT_TEMPLATE_ERROR.message);
        TbAchievementTemplate template = new TbAchievementTemplate();
        template.setAchievementImgUrl(fileUtils.getFileName(vo.getUrl()));
        template.setName(vo.getName());
        int insert = templateMapper.insert(template);
        return ResponseResult.success(insert);
    }

    @Override
    public ResponseResult updateAchievementTemplate(AchievementTemplateVo vo) {
        TbAchievementTemplate template = templateMapper.selectById(vo.getId());
        if(ObjectUtil.isEmpty(template)) throw new CustomException(Status.ACHIEVEMENT_TEMPLATE_NOT_EXIST.code,Status.ACHIEVEMENT_TEMPLATE_NOT_EXIST.message);
        if(StrUtil.isNotBlank(template.getAchievementImgUrl()) && !template.getAchievementImgUrl().equals(fileUtils.getFileName(vo.getUrl()))){
            fileUtils.deleteImageByCloudFlare(template.getAchievementImgUrl());
        }
        template.setAchievementImgUrl(fileUtils.getFileName(vo.getUrl()));
        template.setName(vo.getName());
        int insert = templateMapper.updateById(template);
        return ResponseResult.success(insert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteAchievementTemplate(AchievementTemplateVo vo) {
        TbAchievementTemplate template = templateMapper.selectById(vo.getId());
        if(ObjectUtil.isEmpty(template)) throw new CustomException(Status.ACHIEVEMENT_TEMPLATE_NOT_EXIST.code,Status.ACHIEVEMENT_TEMPLATE_NOT_EXIST.message);
        fileUtils.deleteImageByCloudFlare(template.getAchievementImgUrl());
        templateMapper.deleteById(template.getId());
        achievementMapper.delete(new QueryWrapper<TbAchievement>().lambda()
            .eq(TbAchievement::getAchievementId,vo.getId()));
        return ResponseResult.success();
    }
}
