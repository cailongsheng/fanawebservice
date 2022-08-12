package com.fana.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fana.config.ResponseResult;
import com.fana.entry.pojo.TbMerchant;
import com.fana.entry.vo.IPageVo;
import com.fana.entry.vo.MerchantVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-08-10
 */
public interface ITbMerchantService extends IService<TbMerchant> {

    /**
     * 添加商户信息
     *
     * @param merchantVO MerchantVO
     * @return ResponseResult
     */
    ResponseResult addMerchant(MerchantVO merchantVO);

    /**
     * 更新商户信息
     *
     * @param merchantVO MerchantVO
     * @return ResponseResult
     */
    ResponseResult updateMerchant(MerchantVO merchantVO);

    /**
     * 删除商户信息（逻辑删除）
     *
     * @param merchantVO MerchantVO
     * @return ResponseResult
     */
    ResponseResult deleteMerchant(MerchantVO merchantVO);

    /**
     * 查询商户信息
     *
     * @param merchantVO MerchantVO
     * @return ResponseResult
     */
    ResponseResult queryMerchant(MerchantVO merchantVO);

    /**
     * 分页查询商户信息
     *
     * @param merchantVO MerchantVO
     * @return ResponseResult
     */
    ResponseResult<IPageVo> queryMerchantPage(MerchantVO merchantVO);

    /**
     * 查询商户信息列表
     *
     * @param merchantVO MerchantVO
     * @return ResponseResult <List<MerchantVO>>
     */
    ResponseResult<List<MerchantVO>> queryMerchantList(MerchantVO merchantVO);

}
