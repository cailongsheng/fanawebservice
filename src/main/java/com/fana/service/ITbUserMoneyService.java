package com.fana.service;

import com.fana.config.ResponseResult;
import com.fana.entry.pojo.TbUserMoney;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fana.entry.vo.ApiUserMoneyVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-06-23
 */
public interface ITbUserMoneyService extends IService<TbUserMoney> {

    ResponseResult addUserMoney(ApiUserMoneyVo money);

    ResponseResult checkUserMoney(ApiUserMoneyVo money);

    ResponseResult getUserMoneyList(ApiUserMoneyVo money);

    ResponseResult deleteUserMoney(ApiUserMoneyVo money);
}
