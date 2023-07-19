package com.i2f.train.finance.feign;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.FinanceHold;
import com.i2f.train.starter.model.FinanceOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/02/16:21
 * @Description:
 */
@FeignClient("core")
public interface CoreOpenFeign {
    @PostMapping(value = "/addFinanceOrder", produces = "application/json")
    int addFinanceOrder(@RequestBody FinanceOrder financeOrder);

    @PostMapping(value = "/core/updateMoney", produces = "application/json")
    Result subscribe(@RequestBody UpdateMoneyVO updateMoneyVO);

    @PostMapping(value = "/updateFinanceOrder", produces = "application/json")
    int updateFinance(@RequestBody FinanceOrder financeOrder);

    @PostMapping(value = "/addFinanceHold", produces = "application/json")
    int addFinanceHold(@RequestBody FinanceHold financeHold);

    @PostMapping(value = "/updateFinanceHold", produces = "application/json")
    int updateFinanceHold(@RequestBody FinanceHold financeHold);
}
