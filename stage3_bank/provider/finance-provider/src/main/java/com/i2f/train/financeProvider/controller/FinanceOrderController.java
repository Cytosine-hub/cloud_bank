package com.i2f.train.financeProvider.controller;

import com.i2f.train.financeProvider.service.FinanceOrderService;
import com.i2f.train.starter.model.FinanceOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/02/15:35
 * @Description:
 */
@RestController
@RequestMapping
public class FinanceOrderController {
    @Autowired
    private FinanceOrderService financeOrderService;

    @RequestMapping("/addFinanceOrder")
    public int addFinanceOrder(@RequestBody FinanceOrder financeOrder){
        return financeOrderService.addFinanceOrder(financeOrder);
    }

    @RequestMapping("/updateFinanceOrder")
    public int updateFinanceOrder(@RequestBody FinanceOrder financeOrder) {
        return financeOrderService.updateFinanceOrder(financeOrder);
    }

    @RequestMapping("/selectSubscribeOrder")
    public List<FinanceOrder> selectSubscribeOrderAndNotConfirm() {
        return financeOrderService.selectSubscribeOrderAndNotConfirm();
    }

    @RequestMapping("/selectOrderById")
    public FinanceOrder selectFinanceOrderById(@RequestParam String id) {
        return financeOrderService.selectFinanceOrderById(id);
    }

}
