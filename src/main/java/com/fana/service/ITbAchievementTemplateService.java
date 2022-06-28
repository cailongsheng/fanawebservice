package com.fana.service;

import com.fana.config.ResponseResult;
import com.fana.entry.pojo.TbAchievementTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fana.entry.vo.AchievementTemplateVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-06-08
 */
public interface ITbAchievementTemplateService extends IService<TbAchievementTemplate> {

    ResponseResult getAchievementTemplate();

    ResponseResult addAchievementTemplate(AchievementTemplateVo vo);

    ResponseResult updateAchievementTemplate(AchievementTemplateVo vo);

    ResponseResult deleteAchievementTemplate(AchievementTemplateVo vo);
}
