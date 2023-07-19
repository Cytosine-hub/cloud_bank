package com.i2f.train.core.Service.impl;

import com.i2f.train.core.service.CoreAccountService;
import com.i2f.train.core.feign.AccountProviderOpenFeign;
import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.vo.CheckAccountVO;
import com.i2f.train.starter.model.vo.OpenAccountVo;
import com.i2f.train.starter.model.Account;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-03-30 21:59
 **/
@Service
public class CoreAccountServiceImpl implements CoreAccountService {
    @Resource
    AccountProviderOpenFeign feign;
    @Override
    public Result addAccount(OpenAccountVo openAccountVo,String accountId) {
        int i = feign.openAccount(openAccountVo,accountId);
        if (i==1){
            return Result.success("", CommonConstants.Request.SUCCESS, CommonConstants.OpenAccount.SUCCESS_MESSAGE);
        }
            return Result.error(CommonConstants.OpenAccount.ERROR, CommonConstants.OpenAccount.ERROR_MESSAGE);
    }

    /**
     * 通过身份证、银行卡ID、预留手机号验证信息是否正确
     * @param checkAccount
     * @return boolean
     */
    @Override
    public Result bindCard(CheckAccountVO checkAccount) {
        return feign.bindCar(checkAccount);
    }

    /**
     * 更新银行卡余额
     *
     * @param updateMoney
     * @return Result
     */
    @Override
    public Result updateMoney(UpdateMoneyVO updateMoney) {
        return feign.updateMoney(updateMoney);
    }

    /**
     * 转账
     * @param map
     * @return result
     * */
    @Override
    public Result transfer(Map<String, Object> map) {
        Result result = feign.transfer(map);
        return result;
    }

    /**
     * 查询账户id
     */
    @Override
    public Account selectAccountId(String id){
        Account account = feign.selectByPrimaryKey(id);
        return account;
    }

}
