package com.i2f.train.financeProvider.service;

import com.i2f.train.starter.model.FinanceHold;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.FinanceItem;
import com.i2f.train.starter.model.FinanceOrder;
import com.i2f.train.starter.model.RateRecord;
import com.i2f.train.starter.model.vo.FinanceItemVO;
import com.i2f.train.starter.model.vo.FinanceHoldVO;
import com.i2f.train.starter.model.vo.FinanceItemMoveVo;
import com.i2f.train.starter.model.vo.FinanceOrderVO;
import com.i2f.train.starter.model.queryMap.FinanceQueryMap;
import com.i2f.train.starter.model.vo.HoldRecordVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author: hjx
 * @date: 2022/3/30 13:54
 */
public interface FinanceService {
    /**
     * 查询所有理财产品
     * @return
     */
    Result<List<FinanceItem>> queryAll();

    FinanceItem selectItemById(String id);
    /**
     * 查询finance hold的全部信息
     * @param id
     * @return
     */
    FinanceHold selectholdById(String id);
    /**
     * 根据风险等级基金列表查询
     * @param level
     * @return
     */
    List<FinanceItemMoveVo> selectItemByLevel(String level);

    /**
     * 查某个用户的基金持有表
     * @return
     */

    List<FinanceHoldVO> selectFinanceHold(String userId);

    /**
     * 查询基金详情
     * @param fiId
     * @return
     */

    FinanceItem selectFinanceItemDetail(String fiId);

    FinanceItemVO selectFinanceItemAndRate(String fiId);
    /**
     * 查询基金详情
     * @param queryMap
     * @return
     */
    Result queryFinances(FinanceQueryMap queryMap);

    /**
     * 删除理财产品
     * @param finance
     * @return
     */
    Result deleteFinance(FinanceItem finance);

    /**
     * 修改理财产品
     * @param finance
     * @return
     */
    Result updateFinance(FinanceItem finance);

    /**
     * 修改产品上下架
     * @param finance
     * @return
     */
    Result statusChange(FinanceItem finance);

    /**
     * 添加理财产品
     * @param finance
     * @return
     */
    Result addFinance(FinanceItem finance);

    /**
     * 查询基金的购买订单
     * @param status
     * @return
     */

    List<FinanceOrderVO> queryFinanceOrder(String status,String userId);
    /**
     * 删除用户持仓信息
     */
    int deleteHoldById(String financeHoleId);/**
     * 删除用户持仓信息
     */
    int updateHoldById(FinanceHold financeHold);

    /**
     * 插入赎回数据
     * @param financeOrder
     * @return
     */
    int insertOrder(FinanceOrder financeOrder);
    /**
     * 查询赎回中的产品
     * @param status
     * @param type
     * @return
     */
    List<FinanceOrder> selectOrderByStatus(String status,String type);
    /**
     * 修改order表
     * @param financeOrder
     * @return
     */
    int updateOrder(@RequestBody FinanceOrder financeOrder);
    /**
     * 查询用户是否持有此产品
     * @param userId
     * @param financeId
     * @return
     */
    FinanceHold selectHoldByUserId( String userId,String financeId);

    int addFinanceHold(FinanceHold financeHold);

    int updateFinanceHold(FinanceHold financeHold);

    List<FinanceHold> selectFinanceHoldByIdAndFiID(String userId, String financeId);

    RateRecord selectRateByFiIdAndDate(String financeId, String date);

    int updateFinanceItem(FinanceItem financeItem);

    List<FinanceHold> selectAllHold();

    List<FinanceItem> selectItem();

    int insertRate(RateRecord rateRecord);

    List<FinanceHold> selectHoldByStatus();

    FinanceOrder selectSaleOrderAndNotConfirm(String userId, String financeId);

    HoldRecordVO selectFinanceHoldMoney(String userId);
}
