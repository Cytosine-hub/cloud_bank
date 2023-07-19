package com.i2f.train.userProvider.service;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.UserAccountRelation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/03/30/23:15
 * @Description:
 */
public interface UserAccountRelationService {
    /**
     * 通过用户Id和银行卡Id查询用户是否已经绑定该银行卡
     * @param accountId
     * @param userId
     * @return ArrayList<UserAccountRelation>
     */
    ArrayList<UserAccountRelation> selectByAccountIdAndUserId(String accountId, String userId);

    /**
     * 绑定该银行卡
     * @param userAccountRelation
     * @return boolean
     */
    boolean bindCard(UserAccountRelation userAccountRelation);

    /**
     * 解除银行卡绑定
     * @param accountId
     * @param userId
     * @return Result
     */
    Result removeCard(String accountId,String userId);

    /**
     * 解除全部银行卡绑定
     * @param userId
     * @return Result
     */
    Result removeAllCard(String userId);

    List<UserAccountRelation> selectAccountByUserId(String userId);

    UserAccountRelation selectByAccountAndId(String userId, String accountId);
}
