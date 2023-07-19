package com.i2f.train.financeProvider.controller;

import com.i2f.train.financeProvider.service.FinanceService;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.FinanceHold;
import com.i2f.train.starter.model.FinanceItem;
import com.i2f.train.starter.model.FinanceOrder;
import com.i2f.train.starter.model.RateRecord;
import com.i2f.train.starter.model.vo.FinanceItemVO;
import com.i2f.train.starter.model.vo.FinanceHoldVO;
import com.i2f.train.starter.model.vo.FinanceItemMoveVo;
import com.i2f.train.starter.model.vo.FinanceOrderVO;
import com.i2f.train.starter.model.queryMap.FinanceQueryMap;
import com.i2f.train.starter.model.vo.HoldRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: hjx
 * @date: 2022/3/30 13:53
 */
@RestController
@RequestMapping
public class FinanceController {
    @Autowired
    FinanceService financeService;

    @GetMapping("/queryAll")
    public Result<List<FinanceItem>> queryAll(){
        return financeService.queryAll();
    }
    @RequestMapping("/selectItemById")
    public FinanceItem selectItemById(String id){
        FinanceItem financeItem = financeService.selectItemById(id);
        return financeItem;
    }

    @PostMapping("selectItemByLevel")
    public List<FinanceItemMoveVo> selectItemByLevel(String level){
        List<FinanceItemMoveVo> financeItem = financeService.selectItemByLevel(level);
        return financeItem;
    }
    /**
     * 查询finance hold的全部信息
     * @param id
     * @return
     */
    @PostMapping("/selectHold")
    public FinanceHold selectholdById(@RequestBody String id){
        FinanceHold financeHold = financeService.selectholdById(id);
        return financeHold;
    }
    /**
     * 删除用户持仓信息
     */
    @PostMapping("/deleteHold")
    public int deleteHoldById(@RequestBody String financeHoldId){
       return financeService.deleteHoldById(financeHoldId);
    }
    /**
     * 修改用户持仓信息
     */
    @PostMapping("/updateHold")
    public int updateHoldById(@RequestBody FinanceHold financeHold){
        return financeService.updateHoldById(financeHold);
    }
    /**
     * 插入赎回数据
     * @param financeOrder
     * @return
     */
    @PostMapping("/insertOrder")
    public int insertOrder(@RequestBody FinanceOrder financeOrder){
        return financeService.insertOrder(financeOrder);
    }

    /**
     * 持仓
     * @param userId
     * @return
     */
    @PostMapping("selectFinanceHold")
    public List<FinanceHoldVO> selectFinanceHold(String userId){
        List<FinanceHoldVO> financeHolds=financeService.selectFinanceHold(userId);
        return financeHolds;
    }

    /**
     * 查询基金详情
     * @param fiId
     * @return
     */
    @PostMapping("selectFinanceItemDetail")
    public FinanceItem selectFinanceItemDetail(String fiId){
        FinanceItem financeItem=financeService.selectFinanceItemDetail(fiId);
        return financeItem;
    }

    @PostMapping("/selectFinanceItemAndRate")
    public FinanceItemVO selectFinanceItemAndRate(String fiId) {
        return financeService.selectFinanceItemAndRate(fiId);
    }

    /**
     * 添加理财产品
     * @param finance
     * @return
     */
    @PostMapping("addFinance")
     public Result addFinance(@RequestBody FinanceItem finance){
        return financeService.addFinance(finance);
    }
    /**
     * 查询理财产品
     * @param queryMap
     * @return
     */
    @RequestMapping("queryFinances")
    public Result queryFinances(@RequestBody FinanceQueryMap queryMap){
        return financeService.queryFinances(queryMap);
    }

    /**
     * 删除理财产品
     * @param finance
     * @return
     */
     @PostMapping("deleteFinance")
    public Result deleteFinance(@RequestBody FinanceItem finance){
         return financeService.deleteFinance(finance);
     }

    /**
     * 修改理财产品
     * @param finance
     * @return
     */
     @PostMapping("updateFinance")
    public Result updateFinance(@RequestBody FinanceItem finance){
         return financeService.updateFinance(finance);
     }

    /**
     * 修改理财产品上下架
     * @param finance
     * @return
     */
     @PostMapping("statusChange")
    public Result statusChange(@RequestBody FinanceItem finance){
         return financeService.statusChange(finance);
     }
    /**
     * 查询基金的购买订单
     * @param status
     * @return
     */
    @PostMapping("selectFinanceOrder")
    List<FinanceOrderVO> selectFinanceOrder(String status,String userId){
        List<FinanceOrderVO> financeOrders=financeService.queryFinanceOrder(status,userId);
         return financeOrders;
    }

    /**
     * 查询赎回中的产品
     * @param status
     * @param type
     * @return
     */
    @GetMapping("selectOrderByStatus")
    List<FinanceOrder> selectOrderByStatus(@RequestParam  String status,@RequestParam  String type){
        return financeService.selectOrderByStatus(status,type);
    }

    /**
     * 修改order表
     * @param financeOrder
     * @return
     */
    @PostMapping("updateOrder")
    int updateOrder(@RequestBody FinanceOrder financeOrder){
        return financeService.updateOrder(financeOrder);
    }

    /**
     * 查询用户是否持有此产品
     * @param userId
     * @param financeId
     * @return
     */
    @RequestMapping("selectHoldByUserId")
    FinanceHold selectHoldByUserId(@RequestParam String userId,@RequestParam String financeId){
    return  financeService.selectHoldByUserId(userId,financeId);
    }

    @PostMapping("/addFinanceHold")
    int addFinanceHold(@RequestBody FinanceHold financeHold) {
        return financeService.addFinanceHold(financeHold);
    }

    @PostMapping("/updateFinanceHold")
    int updateFinanceHold(@RequestBody FinanceHold financeHold) {
        return financeService.updateFinanceHold(financeHold);
    }

    @PostMapping("/selectFinanceHoldByIdAndFiId")
    List<FinanceHold> selectFinanceHoldByIdAndFiId(@RequestParam("userId") String userId, @RequestParam("financeId") String financeId){
        return financeService.selectFinanceHoldByIdAndFiID(userId, financeId);
    }

    @PostMapping("/selectRateByFiIdAndDate")
    RateRecord selectRateByFiIdAndDate(@RequestParam("financeId") String financeId, @RequestParam("date") String date) {
        return financeService.selectRateByFiIdAndDate(financeId, date);
    }

    @PostMapping("/updateFinanceItem")
    int updateFinanceItem(@RequestBody FinanceItem financeItem) {
        return financeService.updateFinanceItem(financeItem);
    }

    @PostMapping("/selectAllHold")
    List<FinanceHold> selectAllHold() {
        return financeService.selectAllHold();
    }

    @PostMapping("/selectItem")
    List<FinanceItem> selectItem() {
        return financeService.selectItem();
    }

    @PostMapping("/insertRate")
    int insertRate(@RequestBody RateRecord rateRecord) {
        return financeService.insertRate(rateRecord);
    }

    @PostMapping("/selectHoldByStatus")
    List<FinanceHold> selectHoldByStatus(){
        return financeService.selectHoldByStatus();
    }

    @PostMapping("/selectSaleOrderAndNotConfirm")
    FinanceOrder selectSaleOrderAndNotConfirm(@RequestParam("userId") String userId, @RequestParam("financeId") String financeId){
        return financeService.selectSaleOrderAndNotConfirm(userId, financeId);
    }
    @PostMapping("/selectFinanceHoldMoney")
    HoldRecordVO selectFinanceHoldMoney(String userId){
        return financeService.selectFinanceHoldMoney(userId);
    }
}
