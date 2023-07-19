package com.i2f.train.finance.controller;

import com.i2f.train.finance.feign.FinanceOpenFeign;
import com.i2f.train.finance.service.CustomerFinanceService;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.common.util.JwtUtils;
import com.i2f.train.starter.model.RateRecord;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

import com.i2f.train.starter.common.util.UUIDGenerator;
import com.i2f.train.starter.model.FinanceOrder;

import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.model.OperateRecord;
import com.i2f.train.starter.model.vo.SubscribeVO;
import org.springframework.beans.factory.annotation.Autowired;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/01/14:04
 * @Description:
 */
@RestController
@RequestMapping("/finance")
public class FinanceController {
    @Resource
    CustomerFinanceService customerFinanceService;
    @Autowired
    CustomerFinanceService financeService;
    @Resource
    UUIDGenerator uuidGenerator;
    @Autowired
    FinanceOpenFeign financeOpenFeign;

    @RequestMapping("/financeSubscribe")
    public Result financeSubscribe(@RequestBody SubscribeVO subscribeVO){
        String userId = JwtUtils.getUserId();
        Result result = financeService.beforeSubscribe(userId, subscribeVO);
        if (result != null) {
            return result;
        }

        OperateRecord operateRecord = new OperateRecord();
        operateRecord.setUserId(userId);
        if (!financeService.addOperate(operateRecord)) {
            return Result.error();
        }

        String financeId = subscribeVO.getFinanceId();
        BigDecimal money = subscribeVO.getMoney();
        FinanceOrder financeOrder = new FinanceOrder(uuidGenerator.getId(), financeId, userId, new Timestamp(System.currentTimeMillis()), null, money.toString(),
                CommonConstants.FinanceOrder.ORDER_SUBSCRIBE_TYPE,CommonConstants.FinanceOrder.ORDER_STATUS, "0");
        if (financeService.addFinanceOrder(financeOrder) <= 0) {
            return Result.error();
        }

        UpdateMoneyVO updateMoneyVO = new UpdateMoneyVO();
        updateMoneyVO.setPayMoney(money.toString());
        result = financeService.subscribeUserToBank(userId, updateMoneyVO);
        if (!CommonConstants.Request.SUCCESS.equals(result.getCode())) {
            operateRecord.setStatus(CommonConstants.OperateStatus.FAILED);
            financeService.updateOperate(operateRecord);
            financeOrder.setStatus(CommonConstants.OperateStatus.FAILED);
            financeOrder.setConfirmTime(new Timestamp(System.currentTimeMillis()));
            financeService.updateFinanceOrder(financeOrder);
            return result;
        }

        operateRecord.setStatus(CommonConstants.OperateStatus.SUCCESS);
        if (false == financeService.updateOperate(operateRecord)) {
            financeOrder.setStatus(CommonConstants.OperateStatus.FAILED);
            financeOrder.setConfirmTime(new Timestamp(System.currentTimeMillis()));
            financeService.updateFinanceOrder(financeOrder);
            return Result.error(CommonConstants.Subscribe.SUBSCRIBE_FAILED_ERROR, CommonConstants.Subscribe.SUBSCRIBE_FAILED_MESSAGE);
        }

        return Result.success(CommonConstants.Subscribe.SUBSCRIBE_SUCCESS, CommonConstants.Subscribe.SUBSCRIBING_SUCCESS_MESSAGE);
    }

    /**
     * 基金赎回
     * @param map
     * @return
     */
    @PostMapping("/sale")
    public Result sale(@RequestBody Map<String,String> map){
    return customerFinanceService.sale(map);
    }


    /**
     * 查询理财产品列表
     * @Param level:基金的风险等级 1代表低风险，2代表高风险
     * @return
     */
    @RequestMapping("/financeProductList")
    public Result queryFinanceProductList(String level){
        Result result=customerFinanceService.queryFinanceProduct(level);
        return result;
    }

    /**
     * 查询某个用户理财持有表
     * @return
     */
    @RequestMapping("/queryFinanceHoldList")
    public Result queryFinanceHoldList(){
       Result result= customerFinanceService.queryFinanceHold();
       return result;
    }

    /**
     * 查询理财产品的详情
     * @param fiId
     * @return
     */
    @RequestMapping("/queryFinanceItem")
    public Result financeItemDetail(String fiId){
        Result result=financeService.queryFinanceItemDetail(fiId);
        return result;
    }

    /**
     * 查询基金的购买订单
     * @param status null:全部，2:进行中
     * @return
     */

    @RequestMapping("/financeOrder")
    public Result financeOrder(String status){
        Result result=financeService.queryFinanceOrder(status);
        return result;
    }

    /**
     * 持仓基金的金额
     * @return
     */
    @RequestMapping("/financeHoldMoney")
    public Result financeHoldMoney(){
        Result result=financeService.queryFinanceHoldMoney();
        return result;
    }


    @RequestMapping("/itemAndRate")
    public Result selectFinanceItemAndRate(String fiId){
        return financeService.selectFinanceItemAndRate(fiId);
    }
}
