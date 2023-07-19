package com.i2f.train.financeProvider.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.i2f.train.financeProvider.mapper.FinanceHoldMapper;
import com.i2f.train.financeProvider.mapper.FinanceItemMapper;
import com.i2f.train.financeProvider.mapper.FinanceOrderMapper;
import com.i2f.train.financeProvider.mapper.RateRecordMapper;
import com.i2f.train.financeProvider.service.FinanceService;
import com.i2f.train.financeProvider.utils.todayDateUtil;
import com.i2f.train.starter.common.util.UUIDGenerator;
import com.i2f.train.starter.model.FinanceHold;
import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.constants.FinanceItemConstants;
import com.i2f.train.starter.common.model.Result;

import com.i2f.train.starter.model.FinanceItem;
import com.i2f.train.starter.model.FinanceOrder;
import com.i2f.train.starter.model.RateRecord;
import com.i2f.train.starter.model.vo.FinanceItemVO;
import com.i2f.train.starter.model.vo.FinanceItemMoveVo;
import com.i2f.train.starter.model.vo.FinanceHoldVO;
import com.i2f.train.starter.model.vo.FinanceOrderVO;
import com.i2f.train.starter.model.queryMap.FinanceQueryMap;
import com.i2f.train.starter.model.vo.HoldRecordVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: hjx
 * @date: 2022/3/30 13:55
 */
@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    FinanceItemMapper financeItemMapper;
    @Autowired
    RateRecordMapper rateRecordMapper;
    @Autowired
    FinanceHoldMapper financeHoldMapper;
    @Autowired
    FinanceOrderMapper financeOrderMapper;

    @Resource
    UUIDGenerator uuidGenerator;

    @Override
    public Result<List<FinanceItem>> queryAll() {
        List<FinanceItem> financeItems = financeItemMapper.selectItem();
        return Result.success(financeItems);
    }

    @Override
    public FinanceItem selectItemById(String id) {
        FinanceItem financeItem = financeItemMapper.selectItemById(id);
        if (financeItem == null){
            return null;
        }
        return financeItem;
    }

    /**
     * 查询finance hold的全部信息
     * @param id
     * @return
     */
    @Override
    public FinanceHold selectholdById(String id) {
        FinanceHold financeHold = financeHoldMapper.selectByPrimaryKey(id);
        if (financeHold == null){
            return null;
        }
        return financeHold;
    }

    /**
     * 根据风险等级基金列表查询
     * @param level
     * @return
     */

    @Override
    public List<FinanceItemMoveVo> selectItemByLevel(String level) {
        //总的数据
        List<FinanceItemMoveVo> financeItemVos = new ArrayList<FinanceItemMoveVo>();
        //查询活期的理财产品
        List<FinanceItem> financeItems = financeItemMapper.queryAllFinanceByLevel(level);

        for (FinanceItem financeItem : financeItems) {

            FinanceItemMoveVo financeItemVo = new FinanceItemMoveVo();
            List rateList = new ArrayList<String>();
            //理财的id
            String fiId = financeItem.getId();
            //理财产品
            String fiName = financeItem.getName();
            //最小份额
            String fiMin = financeItem.getMin();
            //最大份额
            String fiMax = financeItem.getMax();
            String todayDate = todayDateUtil.todayDate();
            //昨日利率
            RateRecord yesterdayRateRecord = rateRecordMapper.selectByFIId(fiId, todayDate);
            Double rate=0.00;
            if (yesterdayRateRecord!=null){
                //昨日收益为空值给利率赋值
                rate=yesterdayRateRecord.getRate();
            }
            //根据理财id查询利率变化
            List<RateRecord> financeRates = rateRecordMapper.selectRateRecordByFiId(fiId);
            for (RateRecord financeRate : financeRates) {
                rateList.add(financeRate.getRate());
            }
            financeItemVo.setFiName(fiName);
            financeItemVo.setFiId(fiId);
            financeItemVo.setFinanceRate(rateList);
            financeItemVo.setFiMin(fiMin);
            financeItemVo.setFiMax(fiMax);
            financeItemVo.setYesterdayRate(rate);
            financeItemVos.add(financeItemVo);
        }
        return financeItemVos;
    }
    /**
     * 删除用户持仓信息
     */
    @Override
    public int deleteHoldById(String financeHoldId) {
        return financeHoldMapper.deleteByPrimaryKey(financeHoldId);
    }
    /**
     * 删除用户持仓信息
     */
    @Override
    public int updateHoldById(FinanceHold financeHold) {
        return financeHoldMapper.updateByPrimaryKeySelective(financeHold);
    }

    /**
     * 插入赎回数据
     * @param financeOrder
     * @return
     */
    @Override
    public int insertOrder(FinanceOrder financeOrder) {
        return financeOrderMapper.insert(financeOrder);
    }
    /**
     * 查询赎回中的产品
     * @param status
     * @param type
     * @return
     */
    @Override
    public List<FinanceOrder> selectOrderByStatus(String status, String type) {
        return financeOrderMapper.selectOrderByStatus(status,type);
    }
    /**
     * 修改order表
     * @param financeOrder
     * @return
     */
    @Override
    public int updateOrder(FinanceOrder financeOrder) {
        return financeOrderMapper.updateByPrimaryKeySelective(financeOrder);
    }
    /**
     * 查询用户是否持有此产品
     * @param userId
     * @param financeId
     * @return
     */
    @Override
    public FinanceHold selectHoldByUserId(String userId, String financeId) {
        return financeHoldMapper.selectHoldByUserId(userId,financeId);
    }


    @Override
    public List<FinanceHoldVO> selectFinanceHold(String userId) {
        List<FinanceHoldVO>  financeHoldVOS = new ArrayList<>();
        List<FinanceHold> financeHolds=financeHoldMapper.selectFinanceHoldByUserId(userId);
        for (int i = 0; i < financeHolds.size(); i++) {
            FinanceHoldVO financeHoldVO = new FinanceHoldVO();
            FinanceHold financeHold =  financeHolds.get(i);
            String financeId = financeHold.getFinanceId();
            //根据产品id查询理财产品的名字
            FinanceItem financeItem = financeItemMapper.selectByPrimaryKey(financeId);
            String name = financeItem.getName();
            String level = financeItem.getLevel();
            financeHoldVO.setFinanceItemName(name);
            financeHoldVO.setLevel(level);
            financeHoldVO.setFinanceHold(financeHold);
            financeHoldVOS.add(financeHoldVO);
        }
        return financeHoldVOS;
    }

    /**
     * 查询基金详情
     * @param fiId
     * @return
     */
    @Override
    public FinanceItem selectFinanceItemDetail(String fiId) {
        FinanceItem financeItem = financeItemMapper.selectByPrimaryKey(fiId);
        return financeItem;
    }

    @Override
    public FinanceItemVO selectFinanceItemAndRate(String fiId) {
        ArrayList<Double> rateList = new ArrayList<>();
        FinanceItem financeItem = financeItemMapper.selectByPrimaryKey(fiId);
        List<RateRecord> financeRates = rateRecordMapper.selectRateRecordByFiId(fiId);
        for (RateRecord financeRate : financeRates) {
            rateList.add(financeRate.getRate());
        }
        FinanceItemVO financeItemVO = new FinanceItemVO(financeItem, rateList);
        return financeItemVO;
    }


    /**
     * 添加理财产品
     * @param finance
     * @return
     */
    @Override
    public Result addFinance(FinanceItem finance) {
        String id = uuidGenerator.getId();
        finance.setId(id);
        int i = financeItemMapper.insertSelective(finance);
        if (i==1){
            return Result.success(CommonConstants.Request.SUCCESS,CommonConstants.Request.SUCCESS_MESSAGE);
        }
        return Result.error(FinanceItemConstants.Code.ADD_FAILED,FinanceItemConstants.Message.ADD_FAILED_MESSAGE);
    }

    /**
     * 查询基金的购买订单
     * @param status
     * @return
     */
    @Override
    public List<FinanceOrderVO> queryFinanceOrder(String status,String userId) {
        List<FinanceOrderVO> financeOrderVOS=new ArrayList<>();
        List<FinanceOrder> financeOrders = financeOrderMapper.getOrderList(userId,status);
        for (int i = 0; i < financeOrders.size(); i++) {
            FinanceOrder financeOrder =  financeOrders.get(i);
            FinanceOrderVO financeOrderVO = new FinanceOrderVO();
            financeOrderVO.setId(financeOrder.getId());
            String FiName = financeItemMapper.selectByPrimaryKey(financeOrder.getFinanceId()).getName();
            financeOrderVO.setFiName(FiName);
            financeOrderVO.setFoCreateTime(financeOrder.getCreateTime());
            financeOrderVO.setFoConfirmTime(financeOrder.getConfirmTime());
            financeOrderVO.setFoMoney(financeOrder.getMoney());
            financeOrderVO.setFoType(financeOrder.getType());
            financeOrderVO.setAccount(financeOrder.getAmount());
            financeOrderVO.setStatus(financeOrder.getStatus());
            financeOrderVOS.add(financeOrderVO);

        }
        return financeOrderVOS;
    }

    @Override
    public int addFinanceHold(FinanceHold financeHold) {
        return financeHoldMapper.insertSelective(financeHold);
    }

    @Override
    public int updateFinanceHold(FinanceHold financeHold) {
        return financeHoldMapper.updateByPrimaryKeySelective(financeHold);
    }

    @Override
    public List<FinanceHold> selectFinanceHoldByIdAndFiID(String userId, String financeId) {
        return financeHoldMapper.selectFinanceHOldByIdAndFiId(userId, financeId);
    }

    @Override
    public RateRecord selectRateByFiIdAndDate(String financeId, String date) {
        return rateRecordMapper.selectByFIId(financeId, date);
    }

    @Override
    public int updateFinanceItem(FinanceItem financeItem) {
        return financeItemMapper.updateByPrimaryKeySelective(financeItem);
    }

    @Override
    public List<FinanceHold> selectAllHold() {
        return financeHoldMapper.selectAllHold();
    }

    @Override
    public List<FinanceItem> selectItem() {
        return financeItemMapper.selectItem();
    }

    @Override
    public int insertRate(RateRecord rateRecord) {
        return rateRecordMapper.insertSelective(rateRecord);
    }

    @Override
    public List<FinanceHold> selectHoldByStatus() {
        return financeHoldMapper.selectHoldByStatus();
    }

    @Override
    public FinanceOrder selectSaleOrderAndNotConfirm(String userId, String financeId) {
        return financeOrderMapper.selectSaleOrderAndNotConfirm(userId, financeId);
    }

    /**
     * 查询持仓金额
     * @param userId
     * @return
     */

    @Override
    public HoldRecordVO selectFinanceHoldMoney(String userId) {
        HoldRecordVO holdRecordVO = new HoldRecordVO();
        //稳健总持仓金额
        BigDecimal steadyLevelMoney = new BigDecimal(0);
        try {
            steadyLevelMoney = new BigDecimal(financeHoldMapper.selectSteadyFinanceHoldMoney(userId));
        } catch (Exception e) {
            steadyLevelMoney = steadyLevelMoney;
        }

        //进取总持仓金额
        BigDecimal aggressiveLevelMoney = new BigDecimal(0);
        try {
            aggressiveLevelMoney = new BigDecimal(financeHoldMapper.selectAggressiveFinanceHoldMoney(userId));
        }catch (Exception e) {
            aggressiveLevelMoney = aggressiveLevelMoney;
        }
        //总收益
        BigDecimal totalMoney = new BigDecimal(0);
        totalMoney=totalMoney.add(steadyLevelMoney.add(aggressiveLevelMoney));

        //用户总收益
        BigDecimal totalProfitMoney = new BigDecimal(0);
        try {
            totalProfitMoney = new BigDecimal(financeHoldMapper.selectFinanceHoldTotalProfit(userId));
        } catch (Exception e) {
            totalProfitMoney = totalProfitMoney;
        }

        //用户昨日收益
        BigDecimal profitMoney = new BigDecimal(0);
        try {
            profitMoney = new BigDecimal(financeHoldMapper.selectFinanceHoldProfit(userId));
        } catch (Exception e) {
            profitMoney = profitMoney;
        }


        holdRecordVO.setSteadyLevelMoney(steadyLevelMoney);
        holdRecordVO.setAggressiveLevelMoney(aggressiveLevelMoney);
        holdRecordVO.setTotalMoney(totalMoney);
        holdRecordVO.setTotalProfitMoney(totalProfitMoney);
        holdRecordVO.setProfitMoney(profitMoney);
        return holdRecordVO;
    }


    /**
     * 查询理财产品
     * @param queryMap
     * @return
     */
    @Override
    public Result queryFinances(FinanceQueryMap queryMap) {
        PageHelper.startPage(queryMap.getPage(),queryMap.getPageSize());
        List<FinanceItem> financeItems = financeItemMapper.queryFinances(queryMap);
        if (financeItems != null){
            PageInfo<FinanceItem> finances = new PageInfo<>(financeItems);
            return Result.success(finances, FinanceItemConstants.Code.SUCCESS,FinanceItemConstants.Message.SUCCESS_MESSAGE);
        }
        return Result.error(FinanceItemConstants.Code.SELECT_FAILED,FinanceItemConstants.Message.SELECT_FAILED_MESSAGE);
    }

    /**
     * 删除理财产品
     * @param finance
     * @return
     */
    @Override
    public Result deleteFinance(FinanceItem finance) {
        String id = finance.getId();
        //判断产品
        if (id!=null &&!"".equals(id)){
            //执行删除
            int i = financeItemMapper.deleteFinance(id);
            if (i==1){
                return Result.success(CommonConstants.Request.SUCCESS,FinanceItemConstants.Message.SUCCESS_MESSAGE);
            }else {
                return Result.error(FinanceItemConstants.Code.DELETED_FAILED,FinanceItemConstants.Message.DELETED_FAILED_MESSAGE);
            }
        }
        return null;
    }

    /**
     * 修改理财产品
     * @param finance
     * @return
     */
    @Override
    public Result updateFinance(FinanceItem finance) {
        //判断产品
        if (finance.getId()!=null &&!"".equals(finance.getId())){
            //执行更新
            int i = financeItemMapper.updateByPrimaryKeySelective(finance);
            if (i==1){
                return Result.success(FinanceItemConstants.Code.SUCCESS,FinanceItemConstants.Message.SUCCESS_MESSAGE);
            }else {
                return Result.error(FinanceItemConstants.Code.UPDATE_FAILED,FinanceItemConstants.Message.UPDATE_FAILED_MESSAGE);
            }
        }
        return Result.error(FinanceItemConstants.Code.UPDATE_FAILED,"修改的产品id不能为空");
    }

    /**
     * 修改理财产品上下架
     * @param finance
     * @return
     */
    @Override
    public Result statusChange(FinanceItem finance) {
        String id = finance.getId();
        String isOnsale = finance.getIsOnsale();
        if (StringUtils.isBlank(id)){
            return Result.error(FinanceItemConstants.Code.LEVEL_FAILED,"修改产品的Id不能为空");
        }
        if (StringUtils.isBlank(isOnsale)){
            return Result.error(FinanceItemConstants.Code.LEVEL_FAILED,"修改的产品状态不能为空");
        }
        int i = financeItemMapper.updateIsOnsale(isOnsale, id);
        if (i==1){
            return Result.success(FinanceItemConstants.Code.SUCCESS,FinanceItemConstants.Message.SUCCESS_MESSAGE);
        }
        return Result.error(FinanceItemConstants.Code.LEVEL_FAILED,"修改产品状态失败");
    }
}
