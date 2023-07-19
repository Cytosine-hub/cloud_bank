package com.i2f.train.userProvider.service.impl;

import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.UserAccountRelation;
import com.i2f.train.userProvider.mapper.UserAccountRelationMapper;
import com.i2f.train.userProvider.service.UserAccountRelationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/03/30/23:17
 * @Description:
 */
@Service
public class UserAccountRelationServiceImpl implements UserAccountRelationService {
    @Resource
    UserAccountRelationMapper userAccountRelationMapper;

    @Override
    public List<UserAccountRelation> selectAccountByUserId(String userId) {
        List<UserAccountRelation> userAccountRelations = userAccountRelationMapper.selectAccountByUserId(userId);
        return userAccountRelations;
    }

    @Override
    public UserAccountRelation selectByAccountAndId(String userId, String accountId) {
        UserAccountRelation account = userAccountRelationMapper.selectByAccountAndId(userId, accountId);
        if (account == null) {
            return null;
        }
        return account;
    }

    /**
     * 通过用户Id和银行卡Id查询用户是否已经绑定该银行卡
     * @param accountId
     * @param userId
     * @return UserAccountRelation
     */
    @Override
    public ArrayList<UserAccountRelation> selectByAccountIdAndUserId(String accountId, String userId) {
        //判断信息完不完整
        if (StringUtils.isAnyBlank(accountId,userId)){
            return null;
        }
        UserAccountRelation userAccountRelation = new UserAccountRelation();
        userAccountRelation.setAccountId(accountId);
        userAccountRelation.setUserId(userId);
        //去数据库查询
        ArrayList<UserAccountRelation> userAccountRelations = userAccountRelationMapper.selectByAnyField(userAccountRelation);
        return userAccountRelations;
    }

    /**
     * 绑定该银行卡
     * @param userAccountRelation
     * @return boolean
     */
    @Override
    public boolean bindCard(UserAccountRelation userAccountRelation) {
        if (StringUtils.isAnyBlank(userAccountRelation.getAccountId(),userAccountRelation.getUserId())){
            return false;
        }
        userAccountRelation.setIsMain(CommonConstants.Account.IS_NOT_MAIN_CODE);
        UserAccountRelation userAccountRelation2 = new UserAccountRelation();
        userAccountRelation2.setUserId(userAccountRelation.getUserId());
        //去数据库查询它是否已经绑定有银行卡没有则标记为主卡
        ArrayList<UserAccountRelation> userAccountRelations = userAccountRelationMapper.selectByAnyField(userAccountRelation2);
        if (userAccountRelations == null || userAccountRelations.isEmpty()){
            userAccountRelation.setIsMain(CommonConstants.Account.IS_MAIN_CODE);
        }
        int ok = userAccountRelationMapper.insertSelective(userAccountRelation);
        if (ok > 0){
            return true;
        }
        return false;
    }

    /**
     * 解除银行卡绑定
     * @param accountId
     * @return boolean
     */
    @Override
    public Result removeCard(String accountId,String userId) {
        userAccountRelationMapper.deleteByPrimaryKey(accountId,userId);
        return Result.success("");
    }

    /**
     * 解除全部银行卡绑定
     * @param userId
     * @return Result
     */
    @Override
    public Result removeAllCard(String userId) {
        UserAccountRelation userAccountRelation = new UserAccountRelation();
        userAccountRelation.setUserId(userId);
        userAccountRelationMapper.deleteByUserId(userId);
        return Result.success("");
    }
}
