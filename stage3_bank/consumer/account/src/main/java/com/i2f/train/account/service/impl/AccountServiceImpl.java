package com.i2f.train.account.service.impl;

import com.i2f.train.account.feign.*;
import com.i2f.train.account.service.ConsumerAccountService;
import com.i2f.train.account.utils.CreateBankNumUtil;
import com.i2f.train.account.utils.UUIDGenerator;
import com.i2f.train.account.utils.UUIDUtils;
import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.common.util.JwtUtils;
import com.i2f.train.starter.model.*;
import com.i2f.train.starter.model.vo.BindCardVO;
import com.i2f.train.starter.model.vo.CheckAccountVO;
import com.i2f.train.starter.model.vo.OpenAccountVo;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;
import io.seata.spring.annotation.GlobalLock;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-03-30 21:28
 **/
@Service
public class AccountServiceImpl implements ConsumerAccountService {
    @Resource
    UserAccountRelationOpenFeign userAccountRelationOpenFeign;

    @Resource
    PoliceOpenFeign policeOpenFeign;

    @Resource
    CoreOpenFeign coreOpenFeign;

    @Resource
    UserOpenFeign userOpenFeign;

    @Resource
    UUIDGenerator uuidGenerator;

    @Resource
    UserProviderOpenFeign userProviderOpenFeign;

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    AccountOpenFeign accountOpenFeign;

    /**
     * 开户前的前置校验
     * @param userId
     * @return
     */

    public Result beforeOpenAccount(String userId) {
        //根据用户Id查询用户信息
        User user = userProviderOpenFeign.selectByUserId(userId);
        if (user==null){
            return Result.error(CommonConstants.OpenAccount.USER_NO_EXIST_ERROR,CommonConstants.OpenAccount.USER_NO_EXIST_ERROR_MESSAGE);
        }
        //判断用户状态
        if (!user.getStatus().equals(CommonConstants.OpenAccount.userStatus)){
            return Result.error(CommonConstants.OpenAccount.STATUS_ERROR,CommonConstants.OpenAccount.STATUS_ERROR_MESSAGE);
        }
        //判断用户是否已开户
        if (StringUtils.isNotBlank(user.getAccountId())){
            return Result.error(CommonConstants.OpenAccount.EXIST_ERROR,CommonConstants.OpenAccount.EXIST_ERROR_MESSAGE);
        }
        return Result.success(CommonConstants.Request.SUCCESS,"");
    }

    @Override
    public Result openAccountAuthentication(Police police) {
        String userId = JwtUtils.getUserId();
        //调用开户前信息验证的方法
        Result beforeOpenAccountMessage = beforeOpenAccount(userId);
        if(beforeOpenAccountMessage.getCode()!=CommonConstants.Request.SUCCESS){
            return beforeOpenAccountMessage;
        }
        //公安系统进行信息验证
        int confirm = policeOpenFeign.confirm(police);
        if (confirm == 0) {
            return Result.error(CommonConstants.OpenAccount.PERSONAL_MESSAGE_ERROR,CommonConstants.OpenAccount.PERSONAL_MESSAGE_ERROR_MESSAGE);
        }
        return Result.success(CommonConstants.Request.SUCCESS,CommonConstants.Request.SUCCESS_MESSAGE);
    }

    /**
     * 开户
     * @param openAccountVo
     * @return
     */
    @Override
    public Result openAccount(OpenAccountVo openAccountVo) {
        String userId = JwtUtils.getUserId();
        //添加可疑记录
        OperateRecord operateRecord = new OperateRecord();
        operateRecord.setId(uuidGenerator.getId());
        operateRecord.setUserId(userId);
        operateRecord.setStatus(CommonConstants.OperateStatus.SUSPICIOUS);
        operateRecord.setType(CommonConstants.OperateType.Open_Account);
        userProviderOpenFeign.operateRecordAdd(operateRecord);
        //生成银行卡号
        String accountId = CreateBankNumUtil.generateMasterCardNumber();
        //更新user表和开户相关的字段
        boolean flag = userProviderOpenFeign.updateUser(openAccountVo, userId, accountId);
        if (flag!=true){
            return Result.error(CommonConstants.OpenAccount.ERROR,CommonConstants.OpenAccount.ERROR_MESSAGE);
        }
        //调用核心服务 获取开户结果
        Result result = coreOpenFeign.openAccount(openAccountVo,accountId);

        //修改操作表
        String id = operateRecord.getId();
        if (result.getCode().equals(CommonConstants.OpenAccount.SUCCESS)){
            //将可疑状态修改成成功状态
            String success = CommonConstants.OperateStatus.SUCCESS;
            userProviderOpenFeign.updateOperateRecord(id,success);
        }else {
            //将可疑状态修改成失败状态
            String failed = CommonConstants.OperateStatus.FAILED;
            userProviderOpenFeign.updateOperateRecord(id,failed);
        }
        return result;
    }

    /**
     * 绑定银行卡
     *
     * @param bindCard
     * @return Result
     */
    @Override
    public Result bindCard(BindCardVO bindCard) {
        //从token获取userId
        String userId = JwtUtils.getUserId();
        User user = userProviderOpenFeign.selectByUserId(userId);
        //从redis中获取手机验证码
        String rightCode = (String) redisTemplate.opsForValue().get(bindCard.getPhone());
        //判断是否已经发验证码给该手机
        if (StringUtils.isBlank(rightCode)) {
            return Result.error(CommonConstants.VerificationCode.NOT_GENERATE_CODE, CommonConstants.VerificationCode.NOT_GENERATE_MESSAGE);
        }
        //判断验证码是否正确
        if (!StringUtils.equals(rightCode, bindCard.getUserCode())) {
            return Result.error(CommonConstants.VerificationCode.IMPUTE_ERROR_CODE, CommonConstants.VerificationCode.IMPUTE_ERROR_MESSAGE);
        }
        //增加一条操作记录
        OperateRecord operateRecord = new OperateRecord();
        operateRecord.setType(CommonConstants.OperateType.BIND_CARD);
        operateRecord.setUserId(userId);
        //判断是否为他的二类卡
        if (StringUtils.equals(bindCard.getAccountId(), user.getAccountId())) {
            operateRecord.setStatus(CommonConstants.OperateStatus.FAILED);
            userProviderOpenFeign.operateRecordAdd(operateRecord);
            return Result.error(CommonConstants.Account.SECOND_CODE, CommonConstants.Account.SECOND_MESSAGE);
        }
        //判断是否已经绑定该银行卡
        ArrayList<UserAccountRelation> userAccountRelations = userProviderOpenFeign.selectByAccountIdAndUserId(bindCard.getAccountId(), userId);
        if (userAccountRelations != null && !userAccountRelations.isEmpty()) {
            operateRecord.setStatus(CommonConstants.OperateStatus.FAILED);
            userProviderOpenFeign.operateRecordAdd(operateRecord);
            return Result.error(CommonConstants.Existence.EXISTENCE_CODE, CommonConstants.Existence.EXISTENCE_MESSAGE);
        }
        //去核心验证是否为本人的银行卡
        CheckAccountVO checkAccount = new CheckAccountVO();
        checkAccount.setPersonalId(user.getPersonalId());
        checkAccount.setId(bindCard.getAccountId());
        checkAccount.setPhone(bindCard.getPhone());
        Result have = coreOpenFeign.checkBindCardMessage(checkAccount);
        //判断核心发过来的信息
        if (!StringUtils.equals(have.getCode(), CommonConstants.Request.SUCCESS)) {
            operateRecord.setStatus(CommonConstants.OperateStatus.FAILED);
            userProviderOpenFeign.operateRecordAdd(operateRecord);
            return have;
        }
        //进行绑卡操作
        UserAccountRelation userAccountRelation = new UserAccountRelation();
        userAccountRelation.setUserId(userId);
        userAccountRelation.setAccountId(bindCard.getAccountId());
        userProviderOpenFeign.bindCard(userAccountRelation);
        operateRecord.setStatus(CommonConstants.OperateStatus.SUCCESS);
        userProviderOpenFeign.operateRecordAdd(operateRecord);
        return Result.success("", CommonConstants.Request.SUCCESS, CommonConstants.Request.SUCCESS_MESSAGE);
    }


    /**
     * 更新银行卡余额
     *
     * @param updateMoney
     * @return Result
     */
    @Override
    @GlobalLock
    public Result updateMoney(UpdateMoneyVO updateMoney) {
        //返回结果
        Result result;
        //增加一条操作记录
        OperateRecord operateRecord = new OperateRecord();
        String operateRecordId = uuidGenerator.getId();
        operateRecord.setId(operateRecordId);
        updateMoney.setUuid(operateRecordId);
        //从token中获取用户ID
        String userId = JwtUtils.getUserId();
        //从数据库查询该用户信息
        User user = userProviderOpenFeign.selectByUserId(userId);
        //判断它是否为充值
        if (StringUtils.equals(updateMoney.getType(), CommonConstants.UpdateMoney.RECHARGE_CODE)) {
            //补充没有的信息
            updateMoney.setReceiverAccount(user.getAccountId());
            updateMoney.setReceiverName(user.getName());
            operateRecord.setType(CommonConstants.OperateType.RECHARGE_CARD);
        }
        //判断它是否为提现
        if (StringUtils.equals(updateMoney.getType(), CommonConstants.UpdateMoney.WITHDRAW_CODE)) {
            //补充没有的信息
            updateMoney.setPayAccount(user.getAccountId());
            updateMoney.setReceiverName(user.getName());
            operateRecord.setType(CommonConstants.OperateType.WITHDRAW_CARD);
        }
        //判断它是否为转账
        if (StringUtils.equals(updateMoney.getType(), CommonConstants.UpdateMoney.TRANSFER_CODE)) {
            operateRecord.setType(CommonConstants.OperateType.TRANSFER_CARD);
        }
        //将操作信息打包好
        operateRecord.setUserId(userId);
        operateRecord.setStatus(CommonConstants.OperateStatus.SUSPICIOUS);
        operateRecord.setInAccountId(updateMoney.getReceiverAccount());
        operateRecord.setOutAccountId(updateMoney.getPayAccount());
        //向核心发起请求
        result = coreOpenFeign.updateMoney(updateMoney);
        //根据result修改操作记录的状态
        if (StringUtils.equals(result.getCode(), CommonConstants.Request.SUCCESS)) {
            //成功
            operateRecord.setStatus(CommonConstants.OperateStatus.SUCCESS);
        } else {
            //失败
            operateRecord.setStatus(CommonConstants.OperateStatus.FAILED);
        }
        //将操作记录写入数据库
        userProviderOpenFeign.operateRecordAdd(operateRecord);
        return result;
    }

    /**
     * 查询用户已绑定的银行卡的银行卡Id,银行卡的银行名称,银行卡余额,每日交易限额
     * @return Result
     */
    @Override
    public Result selectBindCard() {
        //从token中获取用户ID
        String userId = JwtUtils.getUserId();
        ArrayList<UserAccountRelation> userAccountRelations = (ArrayList<UserAccountRelation>) userAccountRelationOpenFeign.selectAccountByUserId(userId);
        //筛选数据
        ArrayList<Account> accounts = new ArrayList<>();
        for (UserAccountRelation userAccountRelation:userAccountRelations) {
            Account account = coreOpenFeign.selectByPrimaryKey(userAccountRelation.getAccountId());
            if (account != null) {
                Account account1 = new Account();
                account1.setId(account.getId());
                account1.setRemark1(userAccountRelation.getIsMain());
                account1.setBankName(account.getBankName());
                account1.setMoney(account.getMoney());
                account1.setDailyMoney(account.getDailyMoney());
                accounts.add(account1);
            }
        }
        return Result.success(accounts);
    }

    /**
     * 查询用户银行卡开户行和预留手机号
     * @param accountId
     * @return Result
     */
    @Override
    public Result selectCard(String accountId) {
        //从token中获取用户ID
        String userId = JwtUtils.getUserId();
        User user = userOpenFeign.selectByPrimaryKey(userId);
        Account account = coreOpenFeign.selectByPrimaryKey(accountId);
        //返回需要的内容
        Account account1 = new Account();
        account1.setMobilePhone(account.getMobilePhone());
        account1.setBankName(account.getBankName());
        if (account == null){
            return Result.error(CommonConstants.Existence.NONEXISTENCE_CODE,CommonConstants.Existence.NONEXISTENCE_MESSAGE);
        }
        if (StringUtils.equals(account.getPersonalId(),user.getPersonalId())){
            return Result.success(account1);
        }
        return Result.error(CommonConstants.Existence.NOT_OWN_CODE,CommonConstants.Existence.NOT_OWN_MESSAGE);
    }
    /**
     * 获取卡数，获取总钱数
     */
    @Override
    public Result listBankMoney() {
        //从token获取userId
        String userId = JwtUtils.getUserId();
        List<UserAccountRelation> userAccountRelations = userAccountRelationOpenFeign.selectAccountByUserId(userId);
        BigDecimal total=new BigDecimal(0);
        for ( UserAccountRelation u:userAccountRelations) {
            Account account = accountOpenFeign.queryAccount(u.getAccountId());
            total=total.add(new BigDecimal(account.getMoney()));
        }
        User user = userOpenFeign.selectByPrimaryKey(userId);
        if (user == null){
            return Result.error(CommonConstants.Request.FAILED, "查询失败！");
        }
        Account account = accountOpenFeign.queryAccount(user.getAccountId());
        if (account.getMoney() != null){
            total=total.add(new BigDecimal(account.getMoney()));
        }
        Map map=new HashMap();
        map.put("amount",userAccountRelations.size());
        map.put("money",total);
        return  Result.success(map,CommonConstants.Request.SUCCESS,"查询成功");
    }

    /**
     * 查询用户二类卡余额
     * @return Result
     */
    @Override
    public Result getDiAccount() {
        String userId = JwtUtils.getUserId();
        User user = userOpenFeign.selectByPrimaryKey(userId);
        if (user == null || StringUtils.isBlank(user.getAccountId())){
            return Result.error(CommonConstants.Request.FAILED, "电子账户信息获取失败");
        }
        Account account = accountOpenFeign.queryAccount(user.getAccountId());
        return Result.success(account.getMoney());
    }

    /**
     * 银行卡开户行
     * @param accountId
     * @return Result
     */
    @Override
    public Result selectBankName(String accountId) {
        Account account = coreOpenFeign.selectByPrimaryKey(accountId);
        return Result.success(account.getBankName());
    }


}
