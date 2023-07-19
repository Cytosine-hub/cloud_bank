package com.i2f.train.finance.feign;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.FinanceHold;
import com.i2f.train.starter.model.FinanceHold;
import com.i2f.train.starter.model.FinanceItem;
import com.i2f.train.starter.model.FinanceOrder;
import com.i2f.train.starter.model.RateRecord;
import com.i2f.train.starter.model.vo.FinanceItemVO;
import com.i2f.train.starter.model.vo.FinanceHoldVO;
import com.i2f.train.starter.model.vo.FinanceItemMoveVo;
import com.i2f.train.starter.model.vo.FinanceOrderVO;
import com.i2f.train.starter.model.vo.HoldRecordVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/01/15:25
 * @Description:
 */
@FeignClient("finance-provider")
public interface FinanceOpenFeign {
    @PostMapping(value = "/selectItemById", produces = "application/json")
    FinanceItem selectItemById(@RequestParam String id);

    /**
     * 根据风险查基金
     * @param level
     * @return
     */
    @PostMapping(value = "/selectItemByLevel" ,produces = "application/json")
    List<FinanceItemMoveVo> selectItemByLevel(@RequestParam String level);

    /**
     *  查询finance hold的全部信息
     * @param id
     * @return
     */
    @PostMapping(value = "/selectHold", produces = "application/json")
    public FinanceHold selectholdById(@RequestBody String id);
    /**
     *  查询finance hold的全部信息
     * @param id
     * @return
     */
    @PostMapping(value = "/selectFinanceHold", produces = "application/json")
    public FinanceHold selectholdByFinanceId(@RequestBody String id);
    /**
     * 删除用户持仓信息
     */
    @PostMapping(value="/deleteHold", produces = "application/json")
    public int deleteHoldById(@RequestBody String financeHoldId);
    /**
     * 修改用户持仓信息
     */
    @PostMapping(value="/updateHold",  produces = "application/json")
    public int updateHoldById(@RequestBody FinanceHold financeHold);
    /**
     * 插入赎回数据
     * @param financeOrder
     * @return
     */
    @PostMapping(value ="/insertOrder",produces = "application/json")
    public int insertOrder(@RequestBody FinanceOrder financeOrder);
    /**
     * 查询用户持有表
     * @param userId
     * @return
     */
    @PostMapping(value = "/selectFinanceHold" ,produces = "application/json")
    List<FinanceHoldVO> selectFinanceHold(@RequestParam String userId);

    /**
     * 查询基金详情
     * @param fiId
     * @return
     */
    @PostMapping(value = "/selectFinanceItemDetail",produces = "application/json")
    FinanceItem selectFinanceItemDetail(@RequestParam String fiId);
    /**
     * 查询基金的购买订单
     * @param status
     * @param userId
     * @return
     */
    @PostMapping(value = "/selectFinanceOrder",produces = "application/json")
    List<FinanceOrderVO> selectFinanceOrder(@RequestParam String status,@RequestParam String userId);

    @PostMapping(value = "/selectSubscribeOrder",produces = "application/json")
    List<FinanceOrder> selectSubscribeOrderAndNotConfirm();

    @PostMapping(value = "/selectFinanceHoldByIdAndFiId",produces = "application/json")
    List<FinanceHold> selectFinanceHoldByIdAndFiId(@RequestParam("userId") String userId, @RequestParam("financeId") String financeId);

    @PostMapping(value = "/selectRateByFiIdAndDate",produces = "application/json")
    RateRecord selectRateByFiIdAndDate(@RequestParam("financeId") String financeId, @RequestParam("date") String date);

    @PostMapping(value = "/updateFinanceItem",produces = "application/json")
    int updateFinanceItem(@RequestBody FinanceItem financeItem);

    @PostMapping(value = "/selectAllHold",produces = "application/json")
    List<FinanceHold> selectAllHold();

    @PostMapping(value = "/selectItem",produces = "application/json")
    List<FinanceItem> selectItem();

    @PostMapping(value = "/insertRate",produces = "application/json")
    int insertRate(@RequestBody RateRecord rateRecord);

    /**
     * 查询赎回中的产品
     * @param status
     * @param type
     * @return
     */
    @GetMapping(value= "/selectOrderByStatus",produces = "application/json")
    List<FinanceOrder> selectOrderByStatus( @RequestParam String status,@RequestParam String type);
    /**
     * 修改order表
     * @param financeOrder
     * @return
     */
    @PostMapping(value  ="updateOrder",produces = "application/json")
    int updateOrder(@RequestBody FinanceOrder financeOrder);
    /**
     * 查询用户是否持有此产品
     * @param userId
     * @param financeId
     * @return
     */
    @RequestMapping(value = "selectHoldByUserId", produces = "application/json")
    FinanceHold selectHoldByUserId(@RequestParam String userId,@RequestParam String financeId );

    @PostMapping(value = "/selectOrderById", produces = "application/json")
    FinanceOrder selectFinanceOrderById(@RequestParam String id);

    @PostMapping(value = "/selectHoldByStatus", produces = "application/json")
    List<FinanceHold> selectHoldByStatus();

    @PostMapping(value = "/selectSaleOrderAndNotConfirm", produces = "application/json")
    FinanceOrder selectSaleOrderAndNotConfirm(@RequestParam("userId") String userId, @RequestParam("financeId") String financeId);
    /**
     * 持仓基金的金额
     * @param userId
     * @return
     */
    @PostMapping(value = "/selectFinanceHoldMoney", produces = "application/json")
    HoldRecordVO selectFinanceHoldMoney(@RequestParam String userId);

    @PostMapping(value = "/selectFinanceItemAndRate", produces = "application/json")
    FinanceItemVO selectFinanceItemAndRate(@RequestParam String fiId);
}
