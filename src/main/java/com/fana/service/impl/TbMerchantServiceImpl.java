package com.fana.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fana.config.ResponseResult;
import com.fana.config.Status;
import com.fana.entry.pojo.TbMerchant;
import com.fana.entry.vo.IPageVo;
import com.fana.entry.vo.MerchantVO;
import com.fana.exception.CustomException;
import com.fana.mapper.TbMerchantMapper;
import com.fana.service.ITbMerchantService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-08-10
 */
@Service
@Slf4j
public class TbMerchantServiceImpl extends ServiceImpl<TbMerchantMapper, TbMerchant> implements ITbMerchantService {

    @Resource
    private TbMerchantMapper merchantMapper;

    /**
     * 添加商户信息
     *
     * @param merchantVO MerchantVO
     * @return ResponseResult
     */
    @Override
    public ResponseResult addMerchant(MerchantVO merchantVO) {

        checkMerchantTypeAndKey(merchantVO);

        TbMerchant tbMerchant = new TbMerchant();
        BeanUtils.copyProperties(merchantVO, tbMerchant, "id");
        LocalDateTime now = LocalDateTime.now();
        tbMerchant.setIsDelete(0).setCreateAt(now).setUpdateAt(now);
        if (Objects.equals(tbMerchant.getType(), 1)) {
            tbMerchant.setKey(UUID.randomUUID().toString().replace("-", ""));
        }
        int i = merchantMapper.insert(tbMerchant);
        log.info("FanaWeb|TbMerchantServiceImpl|addMerchant|tbMerchant:{}", JSON.toJSONString(tbMerchant));
        if (i > 0) {
            return ResponseResult.success(tbMerchant.getId());
        }
        return ResponseResult.success(-1);
    }

    /**
     * 更新商户信息
     *
     * @param merchantVO MerchantVO
     * @return ResponseResult
     */
    @Override
    public ResponseResult updateMerchant(MerchantVO merchantVO) {
        if (Objects.isNull(merchantVO.getId()) || Objects.equals(merchantVO.getId(), 0)) {
            throw new CustomException(Status.PARAMETER_VERIFICATION_NOT_PASS.getCode(), Status.PARAMETER_VERIFICATION_NOT_PASS.getMessage());
        }
        checkMerchantTypeAndKey(merchantVO);
        LambdaQueryWrapper<TbMerchant> tbMerchantLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tbMerchantLambdaQueryWrapper.eq(TbMerchant::getId, merchantVO.getId()).eq(TbMerchant::getIsDelete, 0);
        TbMerchant tbMerchant = merchantMapper.selectOne(tbMerchantLambdaQueryWrapper);
        if (Objects.isNull(tbMerchant)) {
            throw new CustomException(Status.QUERY_RESULT_EMPTY.getCode(), Status.QUERY_RESULT_EMPTY.getMessage());
        }
        if (Objects.equals(merchantVO.getType(), 0) && !StringUtils.equals(merchantVO.getKey(), tbMerchant.getKey())) {
            throw new CustomException(Status.PARAMETER_VERIFICATION_NOT_PASS.getCode(), Status.PARAMETER_VERIFICATION_NOT_PASS.getMessage());
        }
        if (Objects.equals(merchantVO.getType(), 1)) {
            BeanUtils.copyProperties(merchantVO, tbMerchant, "key", "isDelete");
        } else {
            BeanUtils.copyProperties(merchantVO, tbMerchant, "isDelete");
        }
        tbMerchant.setUpdateAt(LocalDateTime.now());
        int i = merchantMapper.updateById(tbMerchant);
        log.info("FanaWeb|TbMerchantServiceImpl|updateMerchant|tbMerchant:{}", JSON.toJSONString(tbMerchant));
        if (i > 0) {
            return ResponseResult.success(tbMerchant);
        }
        return ResponseResult.success(-1);

    }

    /**
     * 删除商户信息（逻辑删除）
     *
     * @param merchantVO MerchantVO
     * @return ResponseResult
     */
    @Override
    public ResponseResult deleteMerchant(MerchantVO merchantVO) {
        if (Objects.isNull(merchantVO.getId()) || Objects.equals(merchantVO.getId(), 0)) {
            throw new CustomException(Status.PARAMETER_VERIFICATION_NOT_PASS.getCode(), Status.PARAMETER_VERIFICATION_NOT_PASS.getMessage());
        }
        if (Objects.isNull(queryMerchant(merchantVO).getData())) {
            throw new CustomException(Status.QUERY_RESULT_EMPTY.getCode(), Status.QUERY_RESULT_EMPTY.getMessage());
        }
        LambdaUpdateWrapper<TbMerchant> tbMerchantLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        tbMerchantLambdaUpdateWrapper.eq(TbMerchant::getId, merchantVO.getId()).set(TbMerchant::getIsDelete, 1);
        TbMerchant tbMerchant = new TbMerchant().setIsDelete(1).setUpdateAt(LocalDateTime.now());
        int i = merchantMapper.update(tbMerchant, tbMerchantLambdaUpdateWrapper);
        return ResponseResult.success(i > 0);
    }

    /**
     * 查询商户信息
     *
     * @param merchantVO MerchantVO
     * @return ResponseResult
     */
    @Override
    public ResponseResult queryMerchant(MerchantVO merchantVO) {
        LambdaQueryWrapper<TbMerchant> tbMerchantLambdaQueryWrapper = new LambdaQueryWrapper<TbMerchant>()
                .eq(TbMerchant::getId, merchantVO.getId())
                .eq(TbMerchant::getIsDelete, 0);
        TbMerchant tbMerchant = merchantMapper.selectOne(tbMerchantLambdaQueryWrapper);
        return ResponseResult.success(tbMerchant);
    }

    /**
     * 分页查询商户信息
     *
     * @param merchantVO MerchantVO
     * @return ResponseResult
     */
    @Override
    public ResponseResult<IPageVo> queryMerchantPage(MerchantVO merchantVO) {
        if (Objects.isNull(merchantVO.getPageNum()) || Objects.equals(merchantVO.getPageNum(), 0L)) {
            merchantVO.setPageNum(1L);
        }
        if (Objects.isNull(merchantVO.getPageSize()) || Objects.equals(merchantVO.getPageSize(), 0L)) {
            merchantVO.setPageSize(10L);
        }
        IPage<TbMerchant> pageEntity = new Page<>(merchantVO.getPageNum(), merchantVO.getPageSize());
        LambdaQueryWrapper<TbMerchant> tbMerchantLambdaQueryWrapper = new LambdaQueryWrapper<TbMerchant>()
                .eq(TbMerchant::getIsDelete, 0)
                .like(StringUtils.isNotBlank(merchantVO.getMerchantName()), TbMerchant::getMerchantName, merchantVO.getMerchantName())
                .like(StringUtils.isNotBlank(merchantVO.getMobile()), TbMerchant::getMobile, merchantVO.getMobile())
                .like(StringUtils.isNotBlank(merchantVO.getEmail()), TbMerchant::getEmail, merchantVO.getEmail());
        tbMerchantLambdaQueryWrapper.orderByDesc(TbMerchant::getUpdateAt);
        IPage<TbMerchant> tbMerchantIPage = merchantMapper.selectPage(pageEntity, tbMerchantLambdaQueryWrapper);
        IPageVo iPageVo = new IPageVo();
        if (Objects.nonNull(tbMerchantIPage)) {
            List<MerchantVO> merchantVOList = convertTbMerchantList2MerchantVOList(tbMerchantIPage.getRecords());
            iPageVo.setPageNum(tbMerchantIPage.getCurrent());
            iPageVo.setPageSize(tbMerchantIPage.getSize());
            iPageVo.setTotal(tbMerchantIPage.getTotal());
            iPageVo.setTotalPageCount(tbMerchantIPage.getPages());
            iPageVo.setList(merchantVOList);
        }
        return ResponseResult.success(iPageVo);
    }

    /**
     * 查询商户信息列表
     *
     * @param merchantVO MerchantVO
     * @return ResponseResult <List<MerchantVO>>
     */
    @Override
    public ResponseResult<List<MerchantVO>> queryMerchantList(MerchantVO merchantVO) {
        LambdaQueryWrapper<TbMerchant> tbMerchantLambdaQueryWrapper = new LambdaQueryWrapper<TbMerchant>()
                .eq(TbMerchant::getIsDelete, 0)
                .like(StringUtils.isNotBlank(merchantVO.getMerchantName()), TbMerchant::getMerchantName, merchantVO.getMerchantName())
                .like(StringUtils.isNotBlank(merchantVO.getMobile()), TbMerchant::getMobile, merchantVO.getMobile())
                .like(StringUtils.isNotBlank(merchantVO.getEmail()), TbMerchant::getEmail, merchantVO.getEmail())
                .like(StringUtils.isNotBlank(merchantVO.getKey()), TbMerchant::getKey, merchantVO.getKey())
                .like(StringUtils.isNotBlank(merchantVO.getWebsite()), TbMerchant::getWebsite, merchantVO.getWebsite());
        tbMerchantLambdaQueryWrapper.orderByDesc(TbMerchant::getUpdateAt);
        List<MerchantVO> merchantVOList = convertTbMerchantList2MerchantVOList(merchantMapper.selectList(tbMerchantLambdaQueryWrapper));
        return ResponseResult.success(merchantVOList);
    }

    private List<MerchantVO> convertTbMerchantList2MerchantVOList(List<TbMerchant> tbMerchantList) {
        if (CollectionUtils.isEmpty(tbMerchantList)) {
            return Lists.newArrayList();
        }
        List<MerchantVO> merchantVOList = new ArrayList<>();
        tbMerchantList.forEach(tbMerchant -> {
            MerchantVO merchantVO = new MerchantVO();
            BeanUtils.copyProperties(tbMerchant, merchantVO);
            merchantVOList.add(merchantVO);
        });
        return merchantVOList;
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
