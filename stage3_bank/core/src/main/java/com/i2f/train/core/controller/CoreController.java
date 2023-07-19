package com.i2f.train.core.controller;

import com.i2f.train.core.service.CoreAccountService;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.Account;
import com.i2f.train.starter.model.vo.CheckAccountVO;
import com.i2f.train.starter.model.vo.OpenAccountVo;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-03-30 14:09
 **/
@RestController
@RequestMapping("/core")
public class CoreController {

    @Resource
    CoreAccountService accountService;

    @PostMapping("/open")
    public Result openAccount(@RequestBody OpenAccountVo openAccountVo,String accountId) {
        //A000000
        Result result = accountService.addAccount(openAccountVo, accountId);

        return result;
    }

    @PostMapping("/transfer")
    public Result transfer(@RequestBody Map<String,Object> map){
        Result result = accountService.transfer(map);
        return result;
    }

    /**
     * 通过身份证、银行卡ID、预留手机号验证信息是否正确
     * @param checkAccount
     * @return boolean
     */
    @PostMapping("/bindCard")
    public Result bindCard(@RequestBody CheckAccountVO checkAccount) {
        return accountService.bindCard(checkAccount);
    }

    /**
     * 更新银行卡余额
     *
     * @param updateMoney
     * @return Result
     */
    @PostMapping("/updateMoney")
    public Result updateMoney(@RequestBody UpdateMoneyVO updateMoney) {
        return accountService.updateMoney(updateMoney);
    }

    /**
     * 查询账户id
     */
    @GetMapping("/selectAccountId")
    public Account selectAccountId(@RequestParam String accountId){
        return accountService.selectAccountId(accountId);
    }
}
