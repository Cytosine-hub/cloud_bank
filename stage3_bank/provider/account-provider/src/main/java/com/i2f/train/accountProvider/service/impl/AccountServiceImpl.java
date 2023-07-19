package com.i2f.train.accountProvider.service.impl;

import com.i2f.train.accountProvider.mapper.AccountMapper;
import com.i2f.train.accountProvider.mapper.TransferRecordMapper;
import com.i2f.train.accountProvider.service.AccountService;
import com.i2f.train.accountProvider.service.TransferService;
import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.common.util.UUIDGenerator;
import com.i2f.train.starter.model.Account;
import com.i2f.train.starter.model.OperateRecord;
import com.i2f.train.starter.model.TransferRecord;
import com.i2f.train.starter.model.User;
import com.i2f.train.starter.model.vo.CheckAccountVO;
import com.i2f.train.starter.model.vo.OpenAccountVo;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;
import com.i2f.train.starter.model.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author: 刘志亮
 * @date: 2022/3/30 11:33
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    AccountMapper accountMapper;

    @Resource
    UUIDGenerator uuidGenerator;

    @Resource
    TransferRecordMapper transferRecordMapper;

    @Resource
    TransferService transferService;

    @Override
    public int insertAccountOne(OpenAccountVo openAccountVo,String accountId) {
        Account account = new Account();
        //二类卡
        account.setId(accountId);
        account.setType(CommonConstants.OpenAccount.accountType);
        account.setPayPassword(openAccountVo.getPayPassword());
        account.setBankName(CommonConstants.OpenAccount.BANK_NAME);
        account.setUsername(openAccountVo.getUsername());
        account.setPersonalId(openAccountVo.getPersonalId());
        account.setMoney(CommonConstants.OpenAccount.MONEY);
        account.setDailyMoney(CommonConstants.OpenAccount.DAILY_MONEY);
        account.setCreateTime(new Date());
        //在account表插入数据
        int insert = accountMapper.insertSelective(account);

        return insert;
    }

    /**
     * 通过身份证、银行卡ID、预留手机号验证信息是否正确
     * @param checkAccount
     * @return boolean
     */
    @Override
    public Result selectByAnyField(CheckAccountVO checkAccount) {
        Account account = new Account();
        account.setId(checkAccount.getId());
        ArrayList<Account> accounts = accountMapper.selectByAnyField(account);
        if (accounts.isEmpty()){
            return Result.error(CommonConstants.Existence.NONEXISTENCE_CODE, CommonConstants.Existence.NONEXISTENCE_MESSAGE);
        }
        String personalId = checkAccount.getPersonalId();
        if (!StringUtils.equals(personalId,accounts.get(0).getPersonalId())){
            return Result.error(CommonConstants.Existence.NOT_OWN_CODE, CommonConstants.Existence.NOT_OWN_MESSAGE);
        }
        return Result.success("");
    }

    /**通过银行卡id查询银行卡
     * @param id
     * @return
     */
    @Override
    public Account selectAccountId(String id) {
        if (StringUtils.isBlank(id)){
            return null;
        }
        Account account = new Account();
        account.setId(id);
        account = accountMapper.selectByAnyField(account).get(0);
        return account;
    }

    /**
     * 更新银行卡余额
     * @param updateMoney
     * @return boolean
     */
    @Override
    public Result updateMoney(UpdateMoneyVO updateMoney) {
        //手续费
        BigDecimal otherMoney = null;
        //判断支付方是否为空
        if (StringUtils.isBlank(updateMoney.getPayAccount())){
            return Result.error(CommonConstants.Account.PAY_ACCOUNT_NULL_CODE,CommonConstants.Account.PAY_ACCOUNT_NULL_MESSAGE);
        }
        //判断收款方是否为空
        if (StringUtils.isBlank(updateMoney.getReceiverAccount())){
            return Result.error(CommonConstants.Account.RECEIVER_ACCOUNT_NULL_CODE,CommonConstants.Account.RECEIVER_ACCOUNT_NULL_MESSAGE);
        }
        //查询付款方银行卡信息
        Account payAccount = new Account();
        payAccount.setId(updateMoney.getPayAccount());
        //获取支出银行卡当日已经支出的限额
        BigDecimal dailyMoney = transferService.getAllDailyRecord(updateMoney.getPayAccount());
        //判断支付卡号是否正确
        ArrayList<Account> payAccounts = accountMapper.selectByAnyField(payAccount);
        if (payAccounts.size() != 1){
            return Result.error(CommonConstants.Existence.NONEXISTENCE_CODE, CommonConstants.Existence.NONEXISTENCE_MESSAGE);
        }
        payAccount = payAccounts.get(0);
        //判断支付密码
        if (!StringUtils.equals(payAccount.getPayPassword(),updateMoney.getPayPassword())){
            return Result.error(CommonConstants.UpdateMoney.PAY_PASSWORD_ERROR_CODE, CommonConstants.UpdateMoney.PAY_PASSWORD_ERROR_MESSAGE);
        }
        //判断支付卡号是否已经被冻结
        if (StringUtils.equals(payAccount.getState(),CommonConstants.Account.FREEZE_CODE)){
            return Result.error(CommonConstants.UpdateMoney.PAY_FREEZE_ERROR_CODE, CommonConstants.UpdateMoney.PAY_FREEZE_ERROR_MESSAGE);
        }
        //查询收款方银行卡信息
        Account receiverAccount = new Account();
        receiverAccount.setId(updateMoney.getReceiverAccount());
        ArrayList<Account> receiverAccounts = accountMapper.selectByAnyField(receiverAccount);
        //判断它是否为转账
        if (StringUtils.equals(updateMoney.getType(), CommonConstants.UpdateMoney.TRANSFER_CODE)){
            Result result = transferCheck(payAccount,receiverAccounts,updateMoney.getReceiverName());
            //判断是否符合转账的要求
            if (!StringUtils.equals(result.getCode(),CommonConstants.Request.SUCCESS)){
                return result;
            }
        }
        receiverAccount = receiverAccounts.get(0);
        //用户余额
        BigDecimal payUserMoney = new BigDecimal(payAccount.getMoney()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        //手续费和转账金额
        BigDecimal payAll = new BigDecimal(updateMoney.getPayMoney()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        //判断是否有手续费
        if (!StringUtils.isBlank(updateMoney.getOtherMoney())) {
            otherMoney = new BigDecimal(updateMoney.getOtherMoney()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            payAll = payAll.add(otherMoney);
        }
        //判断加上这次交易是否超过支出银行卡当日已经支出的限额
        dailyMoney = dailyMoney.add(payAll);
        if (dailyMoney.compareTo(payAccount.getDailyMoney()) == 1) {
            return Result.error(CommonConstants.UpdateMoney.DAILY_MONEY_NOT_ENOUGH_CODE,CommonConstants.UpdateMoney.DAILY_MONEY_NOT_ENOUGH_MESSAGE);
        }
        //判断余额是否充足
        if (payUserMoney.compareTo(payAll) == -1 ){
            return Result.error(CommonConstants.UpdateMoney.BALANCE_NOT_ENOUGH_CODE,CommonConstants.UpdateMoney.BALANCE_NOT_ENOUGH_MESSAGE);
        }
        //收取手续费
        if (!StringUtils.isBlank(updateMoney.getOtherMoney())) {
            Account bankAccount = new Account();
            bankAccount.setId(CommonConstants.UpdateMoney.BANK_ACCOUNT);
            bankAccount = accountMapper.selectByAnyField(bankAccount).get(0);
            BigDecimal bankMoney = new BigDecimal(bankAccount.getMoney()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            bankMoney = bankMoney.add(otherMoney);
            bankAccount.setMoney(bankMoney.toString());
            accountMapper.updateByPrimaryKeySelective(bankAccount);
        }
        //修改支付方的银行卡余额
        payUserMoney = payUserMoney.subtract(payAll);
        payAccount.setMoney(payUserMoney.toString());
        //更新支付方的银行卡数据库信息
        accountMapper.updateByPrimaryKeySelective(payAccount);
        //修改收款方的银行卡余额
        BigDecimal receiverUserMoney = new BigDecimal(receiverAccount.getMoney()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        receiverUserMoney = receiverUserMoney.add(new BigDecimal(updateMoney.getPayMoney()).setScale(2, BigDecimal.ROUND_HALF_EVEN));
        receiverAccount.setMoney(receiverUserMoney.toString());
        //更新收款方的银行卡数据库信息
        accountMapper.updateByPrimaryKeySelective(receiverAccount);
        //增加一条记录
        transferAdd(updateMoney);
        return Result.success("");
    }

    /**
     * 增加一条资金变动记录
     * @param updateMoney
     * @return TransferRecord
     */
    public void transferAdd(UpdateMoneyVO updateMoney) {
        TransferRecord transferRecord = new TransferRecord();
        transferRecord.setId(uuidGenerator.getId());
        if (StringUtils.isNotBlank(updateMoney.getMessage())){
            transferRecord.setRemark(updateMoney.getMessage());
        }
        if (StringUtils.isNotBlank(updateMoney.getReceiverAccount())){
            transferRecord.setInAccountId(updateMoney.getReceiverAccount());
        }
        if (StringUtils.isNotBlank(updateMoney.getPayAccount())){
            transferRecord.setOutAccountId(updateMoney.getPayAccount());
        }
        if (StringUtils.isNotBlank(updateMoney.getUuid())){
            transferRecord.setUuid(updateMoney.getUuid());
        }
        if (StringUtils.isNotBlank(updateMoney.getType())){
            transferRecord.setType(updateMoney.getType());
        }
        if (StringUtils.isNotBlank(updateMoney.getOtherMoney())){
            transferRecord.setOtherMoney(new BigDecimal(updateMoney.getOtherMoney()));
        }
        if (StringUtils.isNotBlank(updateMoney.getPayMoney())){
            transferRecord.setMoney(new BigDecimal(updateMoney.getPayMoney()));
        }
        transferRecordMapper.insertSelective(transferRecord);
    }

    /**
     * 转账判断
     * @param receiverAccounts
     * @return Result
     */
    public Result transferCheck(Account payAccount,ArrayList<Account> receiverAccounts,String receiverName) {
        Account receiverAccount;
        //判断收款银行卡号是否正确
        if (receiverAccounts.isEmpty()){
            return Result.error(CommonConstants.UpdateMoney.RECEIVER_CARD_ERROR_CODE, CommonConstants.UpdateMoney.RECEIVER_CARD_ERROR_MESSAGE);
        }
        receiverAccount = receiverAccounts.get(0);
        //判断收款人姓名是否正确
        if (!StringUtils.equals(receiverAccount.getUsername(),receiverName)){
            return Result.error(CommonConstants.UpdateMoney.RECEIVER_NAME_ERROR_CODE, CommonConstants.UpdateMoney.RECEIVER_NAME_ERROR_MESSAGE);
        }
        //是转账则判断两个账号是否为一类卡
        if (!StringUtils.equals(receiverAccount.getType(),CommonConstants.Account.ACCOUNT_TYPE_FIRST_CODE)){
            return Result.error(CommonConstants.UpdateMoney.RECEIVER_CARD_SECOND_CODE, CommonConstants.UpdateMoney.RECEIVER_CARD_SECOND_MESSAGE);
        }
        if (!StringUtils.equals(payAccount.getType(),CommonConstants.Account.ACCOUNT_TYPE_FIRST_CODE)){
            return Result.error(CommonConstants.UpdateMoney.PAY_CARD_SECOND_CODE, CommonConstants.UpdateMoney.PAY_CARD_SECOND_MESSAGE);
        }
        return Result.success("");
    }

    @Override
    public Result transfer(Map<String, Object> map) {
        return null;
    }

    @Override
    public Account selectAccountById(String id) {
        Account account = accountMapper.selectByPrimaryKey(id);
        if (null != account){
            return account;
        }
        return null;
    }

    /**修改账户表支付密码*/
    @Override
    public int upAccountPayPassword(UserVo userVo) {
        Account account=userVo.toAccount();
        account.setPayPassword(userVo.getNewPassword());
        int ok=accountMapper.updateByPrimaryKeySelective(account);
        return ok;
    }


}
