package com.i2f.train.finance.service.impl;

import com.i2f.train.finance.feign.AccountOpenFeign;
import com.i2f.train.finance.feign.CoreOpenFeign;
import com.i2f.train.finance.feign.FinanceOpenFeign;
import com.i2f.train.finance.feign.UserOpenFeign;
import com.i2f.train.finance.service.CustomerFinanceService;
import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.common.util.UUIDGenerator;
import com.i2f.train.starter.model.*;
import com.i2f.train.starter.model.Account;
import com.i2f.train.starter.model.FinanceItem;
import com.i2f.train.starter.model.OperateRecord;
import com.i2f.train.starter.model.User;
import com.i2f.train.starter.common.util.JwtUtils;
import com.i2f.train.starter.model.vo.FinanceItemVO;
import com.i2f.train.starter.model.vo.FinanceItemMoveVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.i2f.train.starter.model.vo.SubscribeVO;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;
import com.i2f.train.starter.model.vo.FinanceOrderVO;
import com.i2f.train.starter.model.vo.HoldRecordVO;
import com.i2f.train.starter.model.vo.FinanceHoldVO;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/01/14:04
 * @Description:
 */
@Service
public class FinanceServiceImpl implements CustomerFinanceService {

    @Autowired
    private UserOpenFeign userOpenFeign;
    @Autowired
    private FinanceOpenFeign financeOpenFeign;
    @Autowired
    private AccountOpenFeign accountOpenFeign;
    @Autowired
    private CoreOpenFeign coreOpenFeign;
    @Resource
    private UUIDGenerator uuidGenerator;

    private final String bankCardId = "0000000000000000000";
    private final String financeCardId = "0000000000000000001";

    @Override
    public Result beforeSubscribe(String userId, SubscribeVO subscribeVO) {

        if (StringUtils.isBlank(userId)){
            return Result.error(CommonConstants.Subscribe.USER_ERROR, CommonConstants.Subscribe.USER_ERROR_MESSAGE);
        }

        User user = userOpenFeign.selectUserById(userId);
        if (user == null){
            return Result.error(CommonConstants.Subscribe.USER_NO_EXIST_ERROR, CommonConstants.Subscribe.USER_NO_EXIST_ERROR_MESSAGE);
        }

        if (subscribeVO == null){
            return Result.error(CommonConstants.Subscribe.SUBSCRIBE_MSG_ERROR, CommonConstants.Subscribe.SUBSCRIBE_MSG_ERROR_MESSAGE);
        }

        String financeId = subscribeVO.getFinanceId();
        BigDecimal money = subscribeVO.getMoney();
        String payPassword = subscribeVO.getPayPassword();
        if (StringUtils.isBlank(financeId) || StringUtils.isBlank(money.toString()) || StringUtils.isBlank(payPassword)){
            return Result.error(CommonConstants.Subscribe.SUBSCRIBE_MSG_ERROR, CommonConstants.Subscribe.SUBSCRIBE_MSG_ERROR_MESSAGE);
        }

        FinanceItem financeItem = financeOpenFeign.selectItemById(financeId);
        if (financeItem == null || CommonConstants.FinanceItem.NOT_ISONSALE.equals(financeItem.getIsOnsale()) || CommonConstants.FinanceItem.ISDELETED.equals(financeItem.getIsdeleted())) {
            return Result.error(CommonConstants.Subscribe.ITEM_ERROR, CommonConstants.Subscribe.ITEM_ERROR_MESSAGE);
        }

        BigDecimal min = new BigDecimal(financeItem.getMin());
        BigDecimal max = new BigDecimal(financeItem.getMax());
        if (money.compareTo(max) == 1 || money.compareTo(min) == -1) {
            return Result.error(CommonConstants.Subscribe.MONEY_ERROR, CommonConstants.Subscribe.MONEY_ERROR_MESSAGE);
        }

        String accountId = user.getAccountId();
        Account account = accountOpenFeign.queryAccount(accountId);
        if (account == null || StringUtils.isBlank(accountId) || CommonConstants.Account.FREEZE_CODE.equals(account.getState())) {
            return Result.error(CommonConstants.Subscribe.ACCOUNT_ERROR, CommonConstants.Subscribe.ACCOUNT_ERROR_MESSAGE);
        }

        if (!payPassword.equals(user.getPayPassword())) {
            return Result.error(CommonConstants.Subscribe.PASSWORD_ERROR, CommonConstants.Subscribe.PASSWORD_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public boolean addOperate(OperateRecord operateRecord) {
        String id = uuidGenerator.getId();
        operateRecord.setId(id);
        User user = userOpenFeign.selectUserById(operateRecord.getUserId());
        String accountId = user.getAccountId();
        operateRecord.setType(CommonConstants.OperateType.SUBSCRIBE);
        operateRecord.setTime(new Timestamp(System.currentTimeMillis()));
        operateRecord.setOutAccountId(accountId);
        operateRecord.setInAccountId(bankCardId);
        return userOpenFeign.operateRecordAdd(operateRecord);
    }

    @Override
    public int addFinanceOrder(FinanceOrder financeOrder) {
        return coreOpenFeign.addFinanceOrder(financeOrder);
    }

    @Override
    public Result subscribeUserToBank(String userId, UpdateMoneyVO updateMoneyVO) {
        User user = userOpenFeign.selectUserById(userId);
        updateMoneyVO.setUuid(uuidGenerator.getId());
        updateMoneyVO.setPayAccount(user.getAccountId());
        updateMoneyVO.setPayPassword(user.getPayPassword());
        updateMoneyVO.setReceiverAccount(bankCardId);
        updateMoneyVO.setType(CommonConstants.UpdateMoney.SUBSCRIBE);
        Account account = accountOpenFeign.queryAccount(bankCardId);
        updateMoneyVO.setReceiverName(account.getUsername());
        return coreOpenFeign.subscribe(updateMoneyVO);
    }

    @Override
    public Result subscribeBankToUser(String userId, UpdateMoneyVO updateMoneyVO) {
        User user = userOpenFeign.selectUserById(userId);
        Account bankAccount = accountOpenFeign.queryAccount(bankCardId);
        updateMoneyVO.setUuid(uuidGenerator.getId());
        updateMoneyVO.setPayAccount(bankCardId);
        updateMoneyVO.setPayPassword(bankAccount.getPayPassword());
        updateMoneyVO.setReceiverAccount(user.getAccountId());
        updateMoneyVO.setReceiverName(user.getName());
        updateMoneyVO.setType(CommonConstants.UpdateMoney.SUBSCRIBE);
        updateMoneyVO.setMessage("申购失败，金额原路返回");
        return coreOpenFeign.subscribe(updateMoneyVO);
    }

    @Override
    public Result subscribeBankToFinance(String financeId, UpdateMoneyVO updateMoneyVO) {
        FinanceItem financeItem = financeOpenFeign.selectItemById(financeId);
        updateMoneyVO.setUuid(uuidGenerator.getId());
        updateMoneyVO.setPayAccount(bankCardId);
        updateMoneyVO.setReceiverAccount(financeCardId);
        updateMoneyVO.setType(CommonConstants.UpdateMoney.SUBSCRIBE);
        Account financeAccount = accountOpenFeign.queryAccount(financeCardId);
        updateMoneyVO.setReceiverName(financeAccount.getUsername());
        Account bankAccount = accountOpenFeign.queryAccount(bankCardId);
        updateMoneyVO.setPayPassword(bankAccount.getPayPassword());
        if (new BigDecimal(updateMoneyVO.getPayMoney()).compareTo(new BigDecimal(financeItem.getShare())) == 1){
            return Result.error(CommonConstants.Subscribe.SHARE_ERROR, CommonConstants.Subscribe.SHARE_ERROR_MESSAGE);
        }
        return coreOpenFeign.subscribe(updateMoneyVO);
    }

    @Override
    public boolean updateOperate(OperateRecord operateRecord) {
        return userOpenFeign.updateOperate(operateRecord);
    }

    @Override
    public int updateFinanceOrder(FinanceOrder financeOrder) {
        return coreOpenFeign.updateFinance(financeOrder);
    }

    @Override
    public int addFinanceHold(FinanceHold financeHold) {
        return coreOpenFeign.addFinanceHold(financeHold);
    }

    @Override
    public int updateFinanceHold(FinanceHold financeHold) {
        return coreOpenFeign.updateFinanceHold(financeHold);
    }

    /**
     * 赎回
     * @param map
     * @return
     */
    @Override
    public Result sale(Map<String, String> map) {
        String  financeHoldId=map.get("financeHoldId");
        String amount=map.get("amount");
        String payPassword=map.get("payPassword");
        //从token中获得userid
        String userId = JwtUtils.getUserId();
        User user = userOpenFeign.selectUserById(userId);
        if (!StringUtils.equals(user.getPayPassword(),payPassword)){
            return Result.error(CommonConstants.Request.PAYPASSWORD_ERROR,CommonConstants.Request.PAYPASSWORD_ERROR_MESSAGE);
        }
        FinanceHold financeHold = financeOpenFeign.selectHoldByUserId(userId,financeHoldId);
        if (Double.parseDouble(financeHold.getAmount())<Double.parseDouble(amount)){
            return  Result.error(CommonConstants.Request.FINANCE_AMOUNT_ERROR,CommonConstants.Request.FINANCE_AMOUNT_ERROR_MESSAGE);
        }
        int i = saleMethod(financeHold, amount);
        if (i!=1){
            return Result.error(CommonConstants.Request.INTERNAL_ERROR,CommonConstants.Request.INTERNAL_ERROR_MESSAGE);
        }
        //剩余份额
        String amountLast= String.valueOf(Double.parseDouble(financeHold.getAmount()) - Double.parseDouble(amount));
        return Result.success(amountLast,CommonConstants.Request.SUCCESS,"赎回申请已提交，请等待");
    }


    /**
     * 基金产品的查询
     * @param level
     * @return
     */
    @Override
    public Result queryFinanceProduct(String level) {
        List<FinanceItemMoveVo> financeItemMoveVos = financeOpenFeign.selectItemByLevel(level);
        return Result.success(financeItemMoveVos);
    }


    /**
     * 减少用户份额
     * @param financeHold
     * @return
     */
    public int saleMethod(FinanceHold financeHold,String amount){
        //添加历史记录

        String money = String.valueOf(new BigDecimal(amount).multiply(new BigDecimal(financeHold.getMoney())).divide(new BigDecimal(financeHold.getAmount())).setScale(2));
        FinanceOrder financeOrder=new FinanceOrder(uuidGenerator.getId(),financeHold.getFinanceId(),financeHold.getUserId(), new Date(),new Date(),money,CommonConstants.FinanceOrder.ORDER_REDEEM_TYPE,CommonConstants.FinanceOrder.ORDER_STATUS,amount);
        financeOpenFeign.insertOrder(financeOrder);

        //修改持仓份额
        if (Double.parseDouble(financeHold.getAmount())==Double.parseDouble(amount)){
            financeHold.setStatus("2");
            financeHold.setMoney(String.valueOf(new BigDecimal(financeHold.getMoney()).subtract(new BigDecimal(money))));
            financeOpenFeign.updateHoldById(financeHold);
        }else {
            double amountLast = Double.parseDouble(financeHold.getAmount()) - Double.parseDouble(amount);
            financeHold.setAmount(String.valueOf(amountLast));
            financeHold.setMoney(String.valueOf(new BigDecimal(financeHold.getMoney()).subtract(new BigDecimal(money))));
            financeOpenFeign.updateHoldById(financeHold);
        }
        return 1;
    }

    /**
     * 查询某个用户的理财持有表
     * @return
     */
    @Override
    public Result queryFinanceHold() {
        String userId = JwtUtils.getUserId();
        List<FinanceHoldVO> financeHolds=financeOpenFeign.selectFinanceHold(userId);
            return Result.success(financeHolds);
    }

    /**
     * 查询某个基金产品的详情
     * @param fiId
     * @return
     */
    @Override
    public Result queryFinanceItemDetail(String fiId) {
        FinanceItem financeItem=financeOpenFeign.selectFinanceItemDetail(fiId);
        return Result.success(financeItem);

    }
    /**
     * 查询基金的购买订单
     * @param status
     * @return
     */

    @Override
    public Result queryFinanceOrder(String status) {
        String userId = JwtUtils.getUserId();
        List<FinanceOrderVO> financeOrders=financeOpenFeign.selectFinanceOrder(status,userId);
            return Result.success(financeOrders);
    }
    /**
     * 持仓基金的金额
     * @return
     */

    @Override
    public Result queryFinanceHoldMoney() {
        String userId = JwtUtils.getUserId();
        HoldRecordVO holdRecordVO=financeOpenFeign.selectFinanceHoldMoney(userId);
        return Result.success(holdRecordVO);
    }

    @Override
    public Result selectFinanceItemAndRate(String fiId) {
        FinanceItemVO financeItemVO = financeOpenFeign.selectFinanceItemAndRate(fiId);
        return Result.success(financeItemVO);
    }
}
