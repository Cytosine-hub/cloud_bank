package com.i2f.train.accountProvider.controller;

import com.i2f.train.accountProvider.service.AccountService;
import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.Account;
import com.i2f.train.starter.model.OperateRecord;
import com.i2f.train.starter.model.vo.CheckAccountVO;
import com.i2f.train.starter.model.vo.OpenAccountVo;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;
import com.i2f.train.starter.model.vo.UserVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 11:28
 */
@RestController
@RequestMapping
public class AccountController {
    @Resource
    AccountService accountService;

    /**
     * 添加开户的操作可疑记录
     * @param operateRecord
     * @return
     */
    @RequestMapping("insertOperateRecord")
    int insertOperateRecord(@RequestBody OperateRecord operateRecord){
        return 1;
    }

    @PostMapping("/insert")
    int insertOne(@RequestBody OpenAccountVo openAccountVo,String accountId) {
        return accountService.insertAccountOne(openAccountVo,accountId);
    }


    /**
     * 验证绑卡时的银行卡信息
     * @param checkAccount
     * @return boolean
     */
    @PostMapping("/bindCar")
    Result binCard(@RequestBody CheckAccountVO checkAccount) {
        return accountService.selectByAnyField(checkAccount);
    }

    /**
     * 验证转账信息
     * */
    @PostMapping("/transfer")
    Result transfer(@RequestParam Map<String, Object> map){
        Result result = accountService.transfer(map);
        return result;
    }

    /**
     * 更改用户余额
     * @param updateMoney
     * @return Result
     */
    @PostMapping("/updateMoney")
    Result updateMoney(@RequestBody UpdateMoneyVO updateMoney) {
        return accountService.updateMoney(updateMoney);
    }

    /**通过银行卡id查询银行卡
     * @param id
     * @return
     */
    @GetMapping("/selectAccountId")
    Account selectByPrimaryKey(@RequestParam String id){
        return accountService.selectAccountId(id);
    }

    @PostMapping("/queryAccount")
    Account queryAccount(@RequestParam("id") String id){
        Account account = accountService.selectAccountById(id);
        return account;
    }

    /**修改账户表支付密码*/
    @RequestMapping("/upAccountPayPassword")
    public int upAccountPayPassword(@RequestBody UserVo userVo){
        int ok=accountService.upAccountPayPassword(userVo);
        return ok;
    }

}
