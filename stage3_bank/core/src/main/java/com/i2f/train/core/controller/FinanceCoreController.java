package com.i2f.train.core.controller;

import com.i2f.train.core.Service.FinanceCoreService;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.FinanceHold;
import com.i2f.train.starter.model.FinanceOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/02/16:09
 * @Description:
 */
@RestController
@RequestMapping
public class FinanceCoreController {
    @Autowired
    private FinanceCoreService financeCoreService;

    @RequestMapping("/addFinanceOrder")
    public int addFinanceOrder(@RequestBody FinanceOrder financeOrder) {
        return financeCoreService.addFinanceOrder(financeOrder);
    }

    @RequestMapping("/subscribe")
    public Result subscribe(@RequestBody UpdateMoneyVO updateMoneyVO) {
        return financeCoreService.subscribe(updateMoneyVO);
    }

    @RequestMapping("/updateFinanceOrder")
    public int updateFinanceOrder(@RequestBody FinanceOrder financeOrder) {
        return financeCoreService.updateFinanceOrder(financeOrder);
    }

    @RequestMapping("/addFinanceHold")
    public int addFinanceHold(@RequestBody FinanceHold financeHold) {
        return financeCoreService.addFinanceHold(financeHold);
    }

    @RequestMapping("/updateFinanceHold")
    public int updateFinanceHold(@RequestBody FinanceHold financeHold) {
        return financeCoreService.updateFinanceHold(financeHold);
    }
}
