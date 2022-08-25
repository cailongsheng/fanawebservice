package com.fana.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.fana.config.ResponseResult;
import com.fana.config.Status;
import com.fana.entry.vo.CharityVo;
import com.fana.entry.vo.GetCharityListVo;
import com.fana.entry.vo.UpdateSortVo;
import com.fana.exception.CustomException;
import com.fana.service.ITbCharityService;
import com.fana.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2022-05-31
 */
@RestController
@RequestMapping("/charity")
@CrossOrigin
@Api("机构")
public class TbCharityController {
    @Resource
    private ITbCharityService charityService;

    @Resource
    private FileUtils fileUtils;


    @PostMapping("/getCharityCategory")
    @ApiOperation("获取机构分类")
    public ResponseResult getCharityCategory(){
        return charityService.getCharityCategory();
    }

    @PostMapping("/fileUpload")
    @ApiOperation(value = "文件上传")
    public ResponseResult fileUpload(MultipartFile file)  {
        if(file == null){
            return new ResponseResult(Status.PARAMETER_ERROR.code, "The file did not fill in  ");
        }
        String upload = null;
        try {
            upload = fileUtils.uploadFile(file,"charity");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(StringUtils.isEmpty(upload)){
            throw new CustomException(Status.IMAGE_UPLOAD_FAILED.getCode(),Status.IMAGE_UPLOAD_FAILED.getMessage());
        }
        return ResponseResult.success(upload);
    }

    @PostMapping("/deleteFile")
    @ApiOperation(value = "删除文件")
    public ResponseResult deleteFile(@RequestBody CharityVo vo){
        if(ObjectUtil.isEmpty(vo)) {
            return new ResponseResult(Status.PARAMETER_ERROR.code, Status.PARAMETER_ERROR.message);
        }
        if(StrUtil.isBlank(vo.getFileUrl())){
            return new ResponseResult(Status.PARAMETER_ERROR.code, "The fileUrl did not fill in  ");
        }
        if(fileUtils.deleteByFile(vo.getFileUrl())){
            return ResponseResult.success();
        }
        return new ResponseResult(Status.FILE_DELETE_ERROR.code,Status.FILE_DELETE_ERROR.message);

    }


    @PostMapping("/getCharityList")
    @ApiOperation("获取机构数据列表")
    public ResponseResult getCharityList(@RequestBody CharityVo vo){
        if(ObjectUtil.isEmpty(vo)) {
            return new ResponseResult(Status.PARAMETER_ERROR.code, Status.PARAMETER_ERROR.message);
        }
        if(ObjectUtil.isEmpty(vo.getPageNum())){
            return new ResponseResult(Status.PARAMETER_ERROR.code, "The pageNum did not fill in  ");
        }
        if(ObjectUtil.isEmpty(vo.getPageSize())){
            return new ResponseResult(Status.PARAMETER_ERROR.code, "The pageSize did not fill in  ");
        }
        return charityService.getCharityList(vo);
    }

    @PostMapping("/updateSort")
    @ApiOperation("/排序接口")
    public ResponseResult updateSort(@RequestBody String json){
        if(StrUtil.isBlank(json)){
            return new ResponseResult(Status.PARAMETER_ERROR.code,Status.PARAMETER_ERROR.message);
        }
        List<UpdateSortVo> updateSortVos = new ArrayList<>();
        JSONArray objects = JSONUtil.parseArray(json);
        objects.stream().forEach(a->{
            UpdateSortVo updateSortVo = JSONUtil.toBean(a.toString(), UpdateSortVo.class);
            updateSortVos.add(updateSortVo);
        });
        return charityService.updateSort(updateSortVos);
    }

    @PostMapping("/addCharity")
    @ApiOperation("添加机构信息")
    public ResponseResult addCharity(@RequestBody GetCharityListVo vo){
        if(ObjectUtil.isEmpty(vo)) {
            return new ResponseResult(Status.PARAMETER_ERROR.code, Status.PARAMETER_ERROR.message);
        }
        if(ObjectUtil.isEmpty(vo.getCategoryId())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The categoryId did not fill in  ");
        if(StrUtil.isBlank(vo.getCharityName())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The charityName did not fill in  ");
        if(StrUtil.isBlank(vo.getDescription())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The description did not fill in  ");
        if(StrUtil.isBlank(vo.getImageUrl())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The imageUrl did not fill in  ");
        if(ObjectUtil.isEmpty(vo.getIsShow())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The isShow did not fill in  ");
        if(StrUtil.isBlank(vo.getWebsite())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The website did not fill in  ");
        return charityService.addCharity(vo);
    }

    @PostMapping("/updateCharity")
    @ApiOperation("更新机构信息")
    public ResponseResult updateCharity(@RequestBody GetCharityListVo vo){
        if(ObjectUtil.isEmpty(vo)) {
            return new ResponseResult(Status.PARAMETER_ERROR.code, Status.PARAMETER_ERROR.message);
        }
        if(ObjectUtil.isEmpty(vo.getId())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The id did not fill in  ");
        if(ObjectUtil.isEmpty(vo.getCategoryId())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The categoryId did not fill in  ");
        if(StrUtil.isBlank(vo.getCharityName())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The charityName did not fill in  ");
        if(StrUtil.isBlank(vo.getDescription())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The description did not fill in  ");
        if(StrUtil.isBlank(vo.getImageUrl())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The imageUrl did not fill in  ");
        if(ObjectUtil.isEmpty(vo.getIsShow())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The isShow did not fill in  ");
        if(StrUtil.isBlank(vo.getWebsite())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The website did not fill in  ");
        return charityService.updateCharity(vo);
    }

    @PostMapping("/deleteCharity")
    @ApiOperation("添加机构信息")
    public ResponseResult deleteCharity(@RequestBody GetCharityListVo vo){
        if(ObjectUtil.isEmpty(vo)) {
            return new ResponseResult(Status.PARAMETER_ERROR.code, Status.PARAMETER_ERROR.message);
        }
        if(ObjectUtil.isEmpty(vo.getId())) return new ResponseResult(Status.PARAMETER_ERROR.code, "The id did not fill in  ");
        return charityService.deleteCharity(vo);
    }

}
