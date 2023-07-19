package com.i2f.train.core.service;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.Account;
import com.i2f.train.starter.model.vo.CheckAccountVO;
import com.i2f.train.starter.model.vo.OpenAccountVo;

import com.i2f.train.starter.model.vo.UpdateMoneyVO;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-03-30 21:57
 **/
public interface CoreAccountService {
    /**
     * 开户方法
     * @param openAccountVo
     * @param accountId
     * @return
     */

    Result addAccount(OpenAccountVo openAccountVo,String accountId);
    /**
     * 通过身份证、银行卡ID、预留手机号验证信息是否正确
     * @param checkAccount
     * @return boolean
     */
    Result bindCard(CheckAccountVO checkAccount);

    /**
     * 更新银行卡余额
     * @param updateMoney
     * @return Result
     */
    Result updateMoney(UpdateMoneyVO updateMoney);

    /**
     * 查询账户id
     */
    Account selectAccountId(String id);

    Result transfer(Map<String, Object> map);
}
