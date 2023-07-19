package com.i2f.train.accountProvider.service;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.Account;
import com.i2f.train.starter.model.OperateRecord;
import com.i2f.train.starter.model.vo.CheckAccountVO;
import com.i2f.train.starter.model.vo.OpenAccountVo;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;
import com.i2f.train.starter.model.vo.UserVo;

import java.util.Map;

import java.math.BigDecimal;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 11:33
 */
public interface AccountService {

    /**
     * 开户
     * @param openAccountVo
     * @param account
     * @return
     */

    int insertAccountOne(OpenAccountVo openAccountVo,String account);

    /**
     * 验证信息是否正确
     * @param checkAccount
     * @return boolean
     */
    Result selectByAnyField(CheckAccountVO checkAccount);

    /**
     * 修改银行卡余额
     * @param updateMoney
     * @return boolean
     */
    Result updateMoney(UpdateMoneyVO updateMoney);


    /**
     * 查询账户id
     * */
    Account selectAccountId(String id);

    Result transfer(Map<String, Object> map);

    Account selectAccountById(String id);

    int upAccountPayPassword(UserVo userVo);
}
