package com.i2f.train.account.controller;

import com.i2f.train.account.service.ConsumerAccountService;
import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.Police;
import com.i2f.train.starter.model.vo.BindCardVO;
import com.i2f.train.starter.model.vo.OpenAccountVo;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;
import com.tencentcloudapi.asr.v20190614.models.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-03-30 21:24
 **/
@RestController
@RequestMapping("/account")
public class AccountController {
    /**
     * /consumerAccount/account/open
     */
    @Resource
    ConsumerAccountService accountService;

    /**
     * 开户前的身份验证
     *
     * @param police
     * @return
     */
    @PostMapping("/openAccountAuthentication")
    Result openAccountAuthentication(@RequestBody Police police) {
        Result result = accountService.openAccountAuthentication(police);
        return result;
    }

    /**
     * 开户
     *
     * @param openAccountVo
     * @return
     */
    @PostMapping("/open")
    public Result openAccount(@RequestBody OpenAccountVo openAccountVo) {
        Result result = accountService.openAccount(openAccountVo);
        return result;
    }

    /**
     * 查询用户银行卡开户行和预留手机号
     *
     * @return
     */
    @GetMapping("/selectCardByAccountId")
    public Result selectCard(String accountId) {
        return accountService.selectCard(accountId);
    }

    /**
     * 查询用户已绑定的银行卡的银行卡Id,银行卡的银行名称,银行卡余额,每日交易限额
     *
     * @return
     */
    @GetMapping("/selectBindCard")
    public Result selectBindCard() {
        return accountService.selectBindCard();
    }

    /**
     * 绑定银行卡
     *
     * @param bindCard
     * @return Result
     */
    @PostMapping("/bindCard")
    public Result bindCard(@RequestBody BindCardVO bindCard) {
        return accountService.bindCard(bindCard);
    }

    /**
     * 转账
     *
     * @param updateMoney
     * @return Result
     */
    @PostMapping("/transfer")
    public Result transfer(@RequestBody UpdateMoneyVO updateMoney) {
        updateMoney.setType(CommonConstants.UpdateMoney.TRANSFER_CODE);
        return accountService.updateMoney(updateMoney);
    }

    /**
     * 提现
     *
     * @param updateMoney
     * @return Result
     */
    @PostMapping("/withdraw")
    public Result withdraw(@RequestBody UpdateMoneyVO updateMoney) {
        updateMoney.setType(CommonConstants.UpdateMoney.WITHDRAW_CODE);
        return accountService.updateMoney(updateMoney);
    }

    /**
     * 充值
     *
     * @param updateMoney
     * @return Result
     */
    @PostMapping("/recharge")
    public Result recharge(@RequestBody UpdateMoneyVO updateMoney) {
        updateMoney.setType(CommonConstants.UpdateMoney.RECHARGE_CODE);
        return accountService.updateMoney(updateMoney);
    }

    /**
     * 获取卡数，获取总钱数
     */
    @GetMapping("listBankMoney")
    public Result listBankMoney() {
        return accountService.listBankMoney();
    }

    /**
     * 查询银行名称
     *
     * @param accountId
     * @return Result
     */
    @GetMapping("/selectBankName")
    public Result selectBankName(String accountId) {
        return accountService.selectBankName(accountId);
    }

    /**
     * 查询用户二类卡余额
     * @return Result
     */
    @GetMapping("/selectUserSecondCard")
    public Result getDiAccountMoney() {
        return accountService.getDiAccount();
    }
}
