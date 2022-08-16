package com.fana.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fana.config.ResponseResult;
import com.fana.config.Status;
import com.fana.entry.vo.AchievementTemplateVo;
import com.fana.exception.CustomException;
import com.fana.service.ITbAchievementTemplateService;
import com.fana.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2022-06-08
 */
@RestController
@RequestMapping("/achievement-template")
@CrossOrigin
@Api("成就徽章")
public class TbAchievementTemplateController {
    @Resource
    private ITbAchievementTemplateService templateService;

    @Resource
    private FileUtils fileUtils;


    @PostMapping("/getAchievementTemplate")
    @ApiOperation("获取徽章模板列表")
    public ResponseResult getAchievementTemplate(){
        return templateService.getAchievementTemplate();
    }

    @PostMapping("/addAchievementTemplate")
    @ApiOperation("添加徽章模板")
    public ResponseResult addAchievementTemplate(@RequestBody AchievementTemplateVo vo){
        if(ObjectUtil.isEmpty(vo)) {
            return new ResponseResult(Status.PARAMETER_ERROR.code, Status.PARAMETER_ERROR.message);
        }
        if(StrUtil.isBlank(vo.getName())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The name did not fill in  ");
        if(StrUtil.isBlank(vo.getUrl())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The url did not fill in  ");
        return templateService.addAchievementTemplate(vo);
    }

    @PostMapping("/fileUpload")
    @ApiOperation(value = "文件上传")
    public ResponseResult fileUpload(MultipartFile file)  {
        if(file == null){
            return new ResponseResult(Status.PARAMETER_ERROR.code, "The file did not fill in  ");
        }
        String upload = null;
        try {
            upload = fileUtils.uploadFile(file,"achievement");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(StringUtils.isEmpty(upload)){
            throw new CustomException(Status.IMAGE_UPLOAD_FAILED.getCode(),Status.IMAGE_UPLOAD_FAILED.getMessage());
        }
        return ResponseResult.success(upload);
    }

    @PostMapping("/updateAchievementTemplate")
    @ApiOperation("修改徽章模板")
    public ResponseResult updateAchievementTemplate(@RequestBody AchievementTemplateVo vo){
        if(ObjectUtil.isEmpty(vo)) return new ResponseResult(Status.PARAMETER_ERROR.code, Status.PARAMETER_ERROR.message);
        if(ObjectUtil.isEmpty(vo.getId())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The id did not fill in  ");
        if(StrUtil.isBlank(vo.getName())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The name did not fill in  ");
        if(StrUtil.isBlank(vo.getUrl())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The url did not fill in  ");
        return templateService.updateAchievementTemplate(vo);
    }

    @PostMapping("/deleteAchievementTemplate")
    @ApiOperation("删除模板")
    public ResponseResult deleteAchievementTemplate(@RequestBody AchievementTemplateVo vo){
        if(ObjectUtil.isEmpty(vo)) return new ResponseResult(Status.PARAMETER_ERROR.code, Status.PARAMETER_ERROR.message);
        if(ObjectUtil.isEmpty(vo.getId())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The id did not fill in  ");
        return templateService.deleteAchievementTemplate(vo);
    }


}
