package com.fana.controller;


import com.alibaba.fastjson.JSON;
import com.fana.config.ResponseResult;
import com.fana.config.Status;
import com.fana.entry.vo.IPageVo;
import com.fana.entry.vo.MerchantVO;
import com.fana.exception.CustomException;
import com.fana.service.ITbMerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2022-08-10
 */
@Api(value = "商户服务", tags = {"商户服务"})
@RestController
@RequestMapping("/merchant")
@Slf4j
public class TbMerchantController {

    @Resource
    private ITbMerchantService merchantService;

    /**
     * 添加商户信息
     *
     * @param merchantVO MerchantVO
     * @return ResponseResult
     */
    @PostMapping("/addMerchant")
    @ApiOperation(value = "添加商户信息")
    public ResponseResult addMerchant(@RequestBody MerchantVO merchantVO) {
        try {
            if (Objects.isNull(merchantVO)) {
                throw new CustomException(Status.PARAMETER_VERIFICATION_NOT_PASS.getCode(), Status.PARAMETER_VERIFICATION_NOT_PASS.getMessage());
            }

            checkMerchantTypeAndKey(merchantVO);

            if (StringUtils.isAnyBlank(merchantVO.getMerchantName(), merchantVO.getWebsite())) {
                throw new CustomException(Status.PARAMETER_VERIFICATION_NOT_PASS.getCode(), Status.PARAMETER_VERIFICATION_NOT_PASS.getMessage());
            }

            if (StringUtils.isBlank(merchantVO.getEmail()) && StringUtils.isBlank(merchantVO.getMobile())) {
                throw new CustomException(Status.PARAMETER_VERIFICATION_NOT_PASS.getCode(), Status.PARAMETER_VERIFICATION_NOT_PASS.getMessage());
            }
            log.info("FanaWeb|TbMerchantController|addMerchant|requestParameters:{}", JSON.toJSONString(merchantVO));
            ResponseResult responseResult = merchantService.addMerchant(merchantVO);
            log.info("FanaWeb|TbMerchantController|addMerchant|responseResult:{}", JSON.toJSONString(responseResult));
            return responseResult;
        } catch (Exception e) {
            log.error("FanaWeb|TbMerchantController|addMerchant|Exception", e);
            throw e;
        }
    }

    /**
     * 更新商户信息
     *
     * @param merchantVO MerchantVO
     * @return ResponseResult
     */
    @PostMapping("/updateMerchant")
    @ApiOperation(value = "更新商户信息")
    public ResponseResult updateMerchant(@RequestBody MerchantVO merchantVO) {
        try {
            if (Objects.isNull(merchantVO)) {
                throw new CustomException(Status.PARAMETER_VERIFICATION_NOT_PASS.getCode(), Status.PARAMETER_VERIFICATION_NOT_PASS.getMessage());
            }
            if (Objects.isNull(merchantVO.getId()) || Objects.equals(merchantVO.getId(), 0)) {
                throw new CustomException(Status.PARAMETER_VERIFICATION_NOT_PASS.getCode(), Status.PARAMETER_VERIFICATION_NOT_PASS.getMessage());
            }
            checkMerchantTypeAndKey(merchantVO);
            log.info("FanaWeb|TbMerchantController|updateMerchant|requestParameters:{}", JSON.toJSONString(merchantVO));
            ResponseResult responseResult = merchantService.updateMerchant(merchantVO);
            log.info("FanaWeb|TbMerchantController|updateMerchant|responseResult:{}", JSON.toJSONString(responseResult));
            return responseResult;
        } catch (Exception e) {
            log.error("FanaWeb|TbMerchantController|updateMerchant|Exception", e);
            throw e;
        }
    }

    /**
     * 删除商户信息（逻辑删除）
     *
     * @param merchantVO MerchantVO
     * @return ResponseResult
     */
    @PostMapping("/deleteMerchant")
    @ApiOperation(value = "删除商户信息")
    public ResponseResult deleteMerchant(@RequestBody MerchantVO merchantVO) {
        try {
            if (Objects.isNull(merchantVO)) {
                throw new CustomException(Status.PARAMETER_VERIFICATION_NOT_PASS.getCode(), Status.PARAMETER_VERIFICATION_NOT_PASS.getMessage());
            }
            if (Objects.isNull(merchantVO.getId()) || Objects.equals(merchantVO.getId(), 0)) {
                throw new CustomException(Status.PARAMETER_VERIFICATION_NOT_PASS.getCode(), Status.PARAMETER_VERIFICATION_NOT_PASS.getMessage());
            }
            log.info("FanaWeb|TbMerchantController|deleteMerchant|requestParameters:{}", JSON.toJSONString(merchantVO));
            ResponseResult responseResult = merchantService.deleteMerchant(merchantVO);
            log.info("FanaWeb|TbMerchantController|deleteMerchant|responseResult:{}", JSON.toJSONString(responseResult));
            return responseResult;
        } catch (Exception e) {
            log.error("FanaWeb|TbMerchantController|deleteMerchant|Exception", e);
            throw e;
        }
    }

    /**
     * 查询商户信息
     *
     * @param merchantVO MerchantVO
     * @return ResponseResult
     */
    @PostMapping("/queryMerchant")
    @ApiOperation(value = "查询商户信息")
    public ResponseResult queryMerchant(@RequestBody MerchantVO merchantVO) {
        try {
            if (Objects.isNull(merchantVO)) {
                throw new CustomException(Status.PARAMETER_VERIFICATION_NOT_PASS.getCode(), Status.PARAMETER_VERIFICATION_NOT_PASS.getMessage());
            }
            if (Objects.isNull(merchantVO.getId()) || Objects.equals(merchantVO.getId(), 0)) {
                throw new CustomException(Status.PARAMETER_VERIFICATION_NOT_PASS.getCode(), Status.PARAMETER_VERIFICATION_NOT_PASS.getMessage());
            }
            log.info("FanaWeb|TbMerchantController|queryMerchant|requestParameters:{}", JSON.toJSONString(merchantVO));
            ResponseResult responseResult = merchantService.queryMerchant(merchantVO);
            log.info("FanaWeb|TbMerchantController|queryMerchant|responseResult:{}", JSON.toJSONString(responseResult));
            return responseResult;
        } catch (Exception e) {
            log.error("FanaWeb|TbMerchantController|queryMerchant|Exception", e);
            throw e;
        }
    }

    /**
     * 分页查询商户信息
     *
     * @param merchantVO MerchantVO
     * @return ResponseResult
     */
    @PostMapping("/queryMerchantPage")
    @ApiOperation(value = "分页查询商户信息")
    public ResponseResult<IPageVo> queryMerchantPage(@RequestBody MerchantVO merchantVO) {
        try {
            if (Objects.isNull(merchantVO)) {
                throw new CustomException(Status.PARAMETER_VERIFICATION_NOT_PASS.getCode(), Status.PARAMETER_VERIFICATION_NOT_PASS.getMessage());
            }
            if (Objects.isNull(merchantVO.getPageNum()) || Objects.equals(merchantVO.getPageNum(), 0L)) {
                merchantVO.setPageNum(1L);
            }
            if (Objects.isNull(merchantVO.getPageSize()) || Objects.equals(merchantVO.getPageSize(), 0L)) {
                merchantVO.setPageSize(10L);
            }
            log.info("FanaWeb|TbMerchantController|queryMerchantPage|requestParameters:{}", JSON.toJSONString(merchantVO));
            ResponseResult<IPageVo> iPageVoResponseResult = merchantService.queryMerchantPage(merchantVO);
            log.info("FanaWeb|TbMerchantController|queryMerchantPage|iPageVoResponseResult IPageVo list size:{}", CollectionUtils.size(iPageVoResponseResult.getData().getList()));
            return iPageVoResponseResult;
        } catch (Exception e) {
            log.error("FanaWeb|TbMerchantController|queryMerchantPage|Exception", e);
            throw e;
        }
    }

    /**
     * 查询商户信息列表
     *
     * @param merchantVO MerchantVO
     * @return ResponseResult <List<MerchantVO>>
     */
    @PostMapping("/queryMerchantList")
    @ApiOperation(value = "查询商户信息列表")
    public ResponseResult<List<MerchantVO>> queryMerchantList(@RequestBody MerchantVO merchantVO) {
        try {
            if (Objects.isNull(merchantVO)) {
                throw new CustomException(Status.PARAMETER_VERIFICATION_NOT_PASS.getCode(), Status.PARAMETER_VERIFICATION_NOT_PASS.getMessage());
            }
            log.info("FanaWeb|TbMerchantController|queryMerchantList|requestParameters:{}", JSON.toJSONString(merchantVO));
            ResponseResult<List<MerchantVO>> listResponseResult = merchantService.queryMerchantList(merchantVO);
            log.info("FanaWeb|TbMerchantController|queryMerchantList|listResponseResult data size:{}", CollectionUtils.size(listResponseResult.getData()));
            return listResponseResult;
        } catch (Exception e) {
            log.error("FanaWeb|TbMerchantController|queryMerchantList|Exception", e);
            throw e;
        }
    }

    /**
     * checkMerchantTypeAndKey
     *
     * @param merchantVO MerchantVO
     */
    private void checkMerchantTypeAndKey(MerchantVO merchantVO) {
        // 0-平台 1-散户
        int[] typeArr = {0, 1};
        if (!ArrayUtils.contains(typeArr, merchantVO.getType())) {
            throw new CustomException(Status.PARAMETER_VERIFICATION_NOT_PASS.getCode(), Status.PARAMETER_VERIFICATION_NOT_PASS.getMessage());
        }
        if (Objects.equals(merchantVO.getType(), 0) && StringUtils.isBlank(merchantVO.getKey())) {
            throw new CustomException(Status.PARAMETER_VERIFICATION_NOT_PASS.getCode(), Status.PARAMETER_VERIFICATION_NOT_PASS.getMessage());
        }
        if (Objects.equals(merchantVO.getType(), 1) && Objects.nonNull(merchantVO.getKey())) {
            throw new CustomException(Status.PARAMETER_VERIFICATION_NOT_PASS.getCode(), Status.PARAMETER_VERIFICATION_NOT_PASS.getMessage());
        }
    }

}
