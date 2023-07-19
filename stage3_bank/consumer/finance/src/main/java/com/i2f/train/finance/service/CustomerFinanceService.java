package com.i2f.train.finance.service;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.FinanceHold;
import com.i2f.train.starter.model.FinanceOrder;
import com.i2f.train.starter.model.OperateRecord;

import java.util.Map;

import com.i2f.train.starter.model.vo.SubscribeVO;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;
/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/01/14:03
 * @Description:
 */
public interface CustomerFinanceService {
    Result beforeSubscribe(String userId, SubscribeVO subscribeVO);

    /**
     * 赎回
     * @param map
     * @return
     */
    Result sale(Map<String,String> map);

    boolean addOperate(OperateRecord operateRecord);

    int addFinanceOrder(FinanceOrder financeOrder);

    Result subscribeUserToBank(String userId, UpdateMoneyVO updateMoneyVO);

     Result subscribeBankToUser(String userId, UpdateMoneyVO updateMoneyVO);

    Result subscribeBankToFinance(String financeId, UpdateMoneyVO updateMoneyVO);

    boolean updateOperate(OperateRecord operateRecord);

    int updateFinanceOrder(FinanceOrder financeOrder);

    int addFinanceHold(FinanceHold financeHold);

    int updateFinanceHold(FinanceHold financeHold);
    /**
     * 基金产品的查询
     * @param level
     * @return
     */
    Result queryFinanceProduct(String level);

    /**
     * 查询某个用户的理财持有表
     * @Param userId
     * @return
     */
    Result queryFinanceHold();

    /**
     * 查询某个基金产品的详情
     * @param fiId
     * @return
     */

    Result queryFinanceItemDetail(String fiId);

    /**
     * 查询基金的购买订单
     * @param status
     * @return
     */
    Result queryFinanceOrder(String status);
        /**
         * 持仓基金的金额
         * @return
         */
    Result queryFinanceHoldMoney();

    Result selectFinanceItemAndRate(String fiId);
}
