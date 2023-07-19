package com.i2f.train.core.feign;

import com.i2f.train.starter.model.FinanceHold;
import com.i2f.train.starter.model.FinanceOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/02/15:38
 * @Description:
 */
@FeignClient("finance-provider")
public interface FinanceOrderOpenFeign {
    @PostMapping(value = "/addFinanceOrder", produces = "application/json")
    int addFinanceOrder(@RequestBody FinanceOrder financeOrder);

    @PostMapping(value = "/updateFinanceOrder", produces = "application/json")
    int updateFinanceOrder(@RequestBody FinanceOrder financeOrder);

    @PostMapping(value = "/addFinanceHold", produces = "application/json")
    int addFinanceHold(@RequestBody FinanceHold financeHold);

    @PostMapping(value = "/updateFinanceHold", produces = "application/json")
    int updateFinanceHold(@RequestBody FinanceHold financeHold);
}
