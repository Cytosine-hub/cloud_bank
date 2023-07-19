package com.i2f.train.finance.service.impl;

import com.i2f.train.finance.common.util.RandomRate;
import com.i2f.train.finance.feign.AccountOpenFeign;
import com.i2f.train.finance.feign.CoreOpenFeign;
import com.i2f.train.finance.feign.FinanceOpenFeign;
import com.i2f.train.finance.service.CustomerFinanceService;
import com.i2f.train.finance.common.util.ExcelUtil;
import com.i2f.train.finance.feign.UserOpenFeign;
import com.i2f.train.finance.service.FinanceTaskService;
import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.common.util.UUIDGenerator;
import com.i2f.train.starter.model.*;
import com.i2f.train.starter.model.vo.BuyFinanceOrderVO;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import static java.math.BigDecimal.ROUND_DOWN;
import static java.math.BigDecimal.ROUND_HALF_UP;
import com.i2f.train.starter.model.Account;
import com.i2f.train.starter.model.FinanceHold;
import com.i2f.train.starter.model.FinanceOrder;
import com.i2f.train.starter.model.User;
import com.i2f.train.starter.model.vo.SubscribeFinanceOrderVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/06/9:27
 * @Description:
 */
@Service
public class FinanceTaskServiceImpl implements FinanceTaskService {

    @Autowired
    private CustomerFinanceService financeService;
    @Autowired
    private FinanceOpenFeign financeOpenFeign;
    @Resource
    private UUIDGenerator uuidGenerator;
    @Autowired
    CoreOpenFeign coreOpenFeign;
    @Autowired
    UserOpenFeign userOpenFeign;
    @Autowired
    AccountOpenFeign accountOpenFeign;

    private static String path = "D:/files/";
//    private static String path = "/usr/local/bank/files/";
    private final String bankCardId = "0000000000000000000";

    @Override
    public void exportSubscribeFile() {
        List<FinanceOrder> financeOrders = financeOpenFeign.selectSubscribeOrderAndNotConfirm();
        if (financeOrders == null || financeOrders.isEmpty()) {
            return;
        }
        ArrayList<Object> list = new ArrayList<>();
        for (FinanceOrder financeOrder : financeOrders) {

            String money = financeOrder.getMoney();
            Date subscribeTime = financeOrder.getCreateTime();
            String id = financeOrder.getId();
            String financeId = financeOrder.getFinanceId();
            String financeName = financeOpenFeign.selectItemById(financeId).getName();
            String username = userOpenFeign.selectUserById(financeOrder.getUserId()).getName();

            String date = String.valueOf(new Timestamp(System.currentTimeMillis())).substring(0,10);
            RateRecord rateRecord = financeOpenFeign.selectRateByFiIdAndDate(financeId, date);
            BigDecimal rate = BigDecimal.valueOf(rateRecord.getRate());
            String amount = new BigDecimal(money).divide(rate, 4, ROUND_DOWN).setScale(2, ROUND_DOWN).toString();

            SubscribeFinanceOrderVO subscribeFinanceOrderVO = new SubscribeFinanceOrderVO(id, username, financeId, financeName, subscribeTime, money, amount);
            list.add(subscribeFinanceOrderVO);
        }
        String date = new Timestamp(System.currentTimeMillis()).toString().substring(0, 10);
        ExcelUtil.exportExcel(list, date+"申购信息","申购申请", SubscribeFinanceOrderVO.class, date+CommonConstants.FileType.SUBSCRIBE,true, null);
    }

    @Override
    public void installRate() {
        List<FinanceItem> financeItems = financeOpenFeign.selectItem();
        for (FinanceItem financeItem : financeItems) {
            Double rate = null;
            if (CommonConstants.FinanceItem.LOW_LEVEL.equals(financeItem.getLevel())) {
                rate = Double.valueOf(RandomRate.getRandomRate(-1d, 3d));
                rate = Double.valueOf(new BigDecimal(rate).divide(new BigDecimal(100)).setScale(4, ROUND_HALF_UP).add(new BigDecimal(1)).multiply(new BigDecimal(5)).setScale(4, ROUND_HALF_UP).toString());
            }
            if (CommonConstants.FinanceItem.HIGH_LEVEL.equals(financeItem.getLevel())) {
                rate = Double.valueOf(RandomRate.getRandomRate(-15d, 30d));
                rate = Double.valueOf(new BigDecimal(rate).divide(new BigDecimal(100)).setScale(4, ROUND_HALF_UP).add(new BigDecimal(1)).multiply(new BigDecimal(5)).setScale(4, ROUND_HALF_UP).toString());
            }

            RateRecord rateRecord = new RateRecord(uuidGenerator.getId(), financeItem.getId(), rate, new Timestamp(System.currentTimeMillis()));
            financeOpenFeign.insertRate(rateRecord);
        }
    }

    @Override
    public void updateMoney() {
        List<FinanceHold> financeHolds = financeOpenFeign.selectHoldByStatus();
        if (financeHolds.size() <= 0) {
            return;
        }
        for (FinanceHold financeHold : financeHolds) {
            String financeId = financeHold.getFinanceId();
            BigDecimal amount = new BigDecimal(financeHold.getAmount());
            String date = String.valueOf(new Timestamp(System.currentTimeMillis())).substring(0,10);
            RateRecord rateRecord = financeOpenFeign.selectRateByFiIdAndDate(financeId, date);
            BigDecimal rate = BigDecimal.valueOf(rateRecord.getRate());
            financeHold.setProfit(amount.multiply(rate).setScale(2, ROUND_HALF_UP).subtract(new BigDecimal(financeHold.getMoney())).toString());
            financeHold.setMoney(amount.multiply(rate).setScale(2, ROUND_HALF_UP).toString());
            financeHold.setTotalProfit(new BigDecimal(financeHold.getProfit()).add(new BigDecimal(financeHold.getTotalProfit())).toString());
            financeService.updateFinanceHold(financeHold);
        }
    }

    @Override
    public void updateStatus() {
        String status = null;
        ArrayList<Object> list = new ArrayList<>();
        String today = new Timestamp(System.currentTimeMillis()).toString().substring(0,10);
        List<SubscribeFinanceOrderVO> financeOrders = ExcelUtil.importExcel(path + today + CommonConstants.FileType.SUBSCRIBE+".xlsx", 1, 1, SubscribeFinanceOrderVO.class);
        if (financeOrders == null || financeOrders.isEmpty()) {
            return;
        }
        for (SubscribeFinanceOrderVO order : financeOrders) {
            FinanceOrder financeOrder = financeOpenFeign.selectFinanceOrderById(order.getId());
            String id = uuidGenerator.getId();
            String userId = financeOrder.getUserId();
            String financeId = financeOrder.getFinanceId();
            String money = financeOrder.getMoney();
            String time = new Timestamp(System.currentTimeMillis() - 86400000).toString().substring(0,10);
            RateRecord rateRecord = financeOpenFeign.selectRateByFiIdAndDate(financeId, time);
            BigDecimal rate = BigDecimal.valueOf(rateRecord.getRate());
            String amount = new BigDecimal(money).divide(rate, 4, ROUND_DOWN).setScale(2, ROUND_DOWN).toString();
            FinanceHold financeHold = new FinanceHold(id, userId, financeId, money, new Timestamp(System.currentTimeMillis()), "0", "0", amount, CommonConstants.FinanceHold.HOLD);

            UpdateMoneyVO updateMoneyVO = new UpdateMoneyVO();
            updateMoneyVO.setPayMoney(money);
            if (new BigDecimal(money).compareTo(new BigDecimal(financeOpenFeign.selectItemById(financeId).getShare()))==1) {
                if (!CommonConstants.Request.SUCCESS.equals(financeService.subscribeBankToUser(userId, updateMoneyVO).getCode())) {
                    financeOrder.setStatus(CommonConstants.OperateStatus.FAILED);
                    financeOrder.setConfirmTime(new Timestamp(System.currentTimeMillis()));
                    financeService.updateFinanceOrder(financeOrder);
                    return;
                }
            }
            Result result = financeService.subscribeBankToFinance(financeId, updateMoneyVO);
            if (!CommonConstants.Request.SUCCESS.equals(result.getCode())) {
                financeOrder.setStatus(CommonConstants.OperateStatus.FAILED);
                financeOrder.setConfirmTime(new Timestamp(System.currentTimeMillis()));
                financeService.updateFinanceOrder(financeOrder);
                return;
            }

            FinanceItem financeItem = financeOpenFeign.selectItemById(financeId);
            financeItem.setShare(new BigDecimal(financeItem.getShare()).subtract(new BigDecimal(money)).toString());
            financeOpenFeign.updateFinanceItem(financeItem);

            financeOrder.setStatus(CommonConstants.OperateStatus.SUCCESS);
            financeOrder.setConfirmTime(new Timestamp(System.currentTimeMillis()));
            financeOrder.setAmount(amount);
            financeService.updateFinanceOrder(financeOrder);
            List<FinanceHold> financeHolds = financeOpenFeign.selectFinanceHoldByIdAndFiId(userId, financeId);
            if (financeHolds.size() == 0 || CommonConstants.OperateStatus.SUSPICIOUS.equals(financeHolds.get(0).getStatus())) {
                financeService.addFinanceHold(financeHold);
            } else {
                FinanceHold hold = financeHolds.get(0);
                hold.setMoney(new BigDecimal(hold.getMoney()).add(new BigDecimal(money)).toString());
                hold.setAmount(new BigDecimal(hold.getAmount()).add(new BigDecimal(amount)).toString());
                financeService.updateFinanceHold(hold);
            }
            if (CommonConstants.OperateStatus.FAILED.equals(financeOrder.getStatus())) {
                status = CommonConstants.OperateStatus.FAILED_MESSAGE;
            }
            if (CommonConstants.OperateStatus.SUCCESS.equals(financeOrder.getStatus())) {
                status = CommonConstants.OperateStatus.SUCCESS_MESSAGE;
            }
            BuyFinanceOrderVO buyFinanceOrderVO = new BuyFinanceOrderVO(order.getId(), order.getUsername(), order.getFinanceId(), order.getFinanceName(), new Date(), order.getMoney(), order.getAmount(), status);
            list.add(buyFinanceOrderVO);
        }
        String date = new Timestamp(System.currentTimeMillis()-86400000).toString().substring(0, 10);
        ExcelUtil.exportExcel(list, date+"已购信息","已购信息", BuyFinanceOrderVO.class, date+CommonConstants.FileType.SUBSCRIBE_BACK,true, null);
    }

    /**
     * 将需要赎回的申请导出成excel
     */
    @Override
    public void saleBackPartsExcel() {
        //查询申请赎回的产品
        List<FinanceOrder> financeOrders = financeOpenFeign.selectOrderByStatus(CommonConstants.FinanceOrder.ORDER_STATUS, CommonConstants.FinanceOrder.ORDER_REDEEM_TYPE);
        String date=new Timestamp(System.currentTimeMillis()).toString().substring(0,10);
        ExcelUtil.exportExcel(financeOrders,date+"赎回信息","赎回申请",FinanceOrder.class,date+CommonConstants.FileType.SALE,true, null );


    }

    /**
     *赎回
     */
    @Override
    public void saleBackParts() {
        String today = new Timestamp(System.currentTimeMillis()).toString().substring(0,10);
       //导入excel
        List<FinanceOrder> financeOrders = ExcelUtil.importExcel(path+today+CommonConstants.FileType.SALE_BACK+".xlsx", 1, 1, FinanceOrder.class);
        if (financeOrders==null){
            return;
        }
        for ( FinanceOrder financeOrder:financeOrders) {
            saleMotheds(financeOrder);

        }

    }


    @Transactional(rollbackFor = Exception.class)
    public int saleMotheds(FinanceOrder financeOrder){
        FinanceHold financeHold = financeOpenFeign.selectHoldByUserId(financeOrder.getUserId(), financeOrder.getFinanceId());
        if ("0".equals(financeOrder.getStatus())){
            financeOrder.setConfirmTime(new Date());
            financeOrder.setStatus("0");
            financeOpenFeign.updateOrder(financeOrder);
            if ("2".equals(financeHold.getStatus())){
                financeHold.setStatus("1");
                financeOpenFeign.updateHoldById(financeHold);
            }
            financeHold.setAmount(String.valueOf(Integer.parseInt(financeHold.getAmount())+Integer.parseInt(financeOrder.getAmount())));
        }

        BigDecimal money = new BigDecimal(financeOrder.getMoney());
        User user = userOpenFeign.selectUserById(financeOrder.getUserId());

        Account account1 = accountOpenFeign.queryAccount(bankCardId);
        UpdateMoneyVO updateMoneyVO=new UpdateMoneyVO(account1.getPayPassword(),bankCardId,money.toString(),user.getAccountId(),user.getName(),"4");
         coreOpenFeign.subscribe(updateMoneyVO);

        financeOrder.setStatus("1");
        financeOrder.setConfirmTime(new Date());
        financeOpenFeign.updateOrder(financeOrder);
        return 1;
    }
    /**
     * 基金公司赎回回盘
     */
    @Override
    public  void  excelsaleback(){
        String today = new Timestamp(System.currentTimeMillis()).toString().substring(0,10);
        List<FinanceOrder> financeOrdersBack=new LinkedList<>();
        //导入excel
        List<FinanceOrder> financeOrders = ExcelUtil.importExcel(path+today+CommonConstants.FileType.SALE+".xlsx", 1, 1, FinanceOrder.class);
        if (financeOrders==null){
            return;
        }
        for ( FinanceOrder financeOrder:financeOrders) {
        financeOrder.setStatus("1");
        financeOpenFeign.updateOrder(financeOrder);
        financeOrdersBack.add(financeOrder);
        }
        if (financeOrdersBack==null){
            return;
        }
        String date=new Timestamp(System.currentTimeMillis()).toString().substring(0,10);
        ExcelUtil.exportExcel(financeOrdersBack,date+"赎回信息","赎回申请",FinanceOrder.class,date+CommonConstants.FileType.SALE_BACK,true, null );

    }

}
