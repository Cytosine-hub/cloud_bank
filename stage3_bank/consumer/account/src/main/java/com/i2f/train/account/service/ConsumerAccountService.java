package com.i2f.train.account.service;


import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.Police;
import com.i2f.train.starter.model.vo.BindCardVO;
import com.i2f.train.starter.model.vo.OpenAccountVo;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;


import java.math.BigDecimal;
import java.util.Map;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-03-30 21:27
 **/
public interface ConsumerAccountService {
    /**
     * 开户前的身份验证

     * @return
     */

    Result openAccountAuthentication(Police police);

    /**
     * 开户
     * @param openAccountVo
     * @return
     */
    Result openAccount(OpenAccountVo openAccountVo);

    /**
     * 绑定银行卡
     * @param bindCard
     * @return Result
     */
    Result bindCard(BindCardVO bindCard);

    /**
     * 更新银行卡余额
     * @param updateMoney
     * @return Result
     */
    Result updateMoney(UpdateMoneyVO updateMoney);

    /**
     * 查询用户已绑定的银行卡
     * @return Result
     */
    Result selectBindCard();

    /**
     * 查询用户银行卡
     * @param accountId
     * @return Result
     */
    Result selectCard(String accountId);

    /**
     * 银行卡开户行
     * @param accountId
     * @return Result
     */
    Result selectBankName(String accountId);

    /**
     * 获取卡数，获取总钱数
     */
    Result listBankMoney();

    /**
     * 查询用户二类卡余额
     * @return Result
     */
    Result getDiAccount();
}
