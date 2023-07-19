package com.i2f.train.userProvider.controller;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.UserAccountRelation;
import com.i2f.train.userProvider.service.UserAccountRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/03/30/23:20
 * @Description:
 */
@RestController
@RequestMapping("/userAccountRelation")
public class UserAccountRelationController {
    @Resource
    UserAccountRelationService userAccountRelationService;

    @RequestMapping("/queryAccounts")
    public List<UserAccountRelation> selectAccountByUserId(@RequestParam String userId) {
        List<UserAccountRelation> userAccountRelations = userAccountRelationService.selectAccountByUserId(userId);
        return userAccountRelations;
    }
    /**
     * 通过用户Id和银行卡Id查询用户是否已经绑定该银行卡
     * @param accountId
     * @param userId
     * @return ArrayList<UserAccountRelation>
     */
    @PostMapping("selectByAccountIdAndUserId")
    public ArrayList<UserAccountRelation> selectByAccountIdAndUserId(@RequestParam String accountId, @RequestParam String userId) {
        return userAccountRelationService.selectByAccountIdAndUserId(accountId, userId);
    }

    /**
     * 绑定该银行卡
     * @param userAccountRelation
     * @return boolean
     */
    @PostMapping("bindCard")
    public boolean bindCard(@RequestBody UserAccountRelation userAccountRelation) {
        return userAccountRelationService.bindCard(userAccountRelation);
    }

    /**
     * 解除银行卡绑定
     * @param accountId
     * @return boolean
     */
    @PostMapping("removeCard")
    public Result removeCard(@RequestParam String accountId,@RequestParam String userId) {
        return userAccountRelationService.removeCard(accountId, userId);
    }

    /**
     * 解除全部银行卡绑定
     * @param userId
     * @return boolean
     */
    @PostMapping("removeAllCard")
    public Result removeAllCard(@RequestParam String userId) {
        return userAccountRelationService.removeAllCard(userId);
    }

    @PostMapping("/selectByAccountAndId")
    public UserAccountRelation selectByAccountAndId(@RequestParam String userId, @RequestParam String accountId) {
        return userAccountRelationService.selectByAccountAndId(userId, accountId);
    }
}
