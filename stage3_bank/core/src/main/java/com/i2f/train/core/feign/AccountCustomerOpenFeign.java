package com.i2f.train.core.feign;

import com.i2f.train.starter.common.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/02/15:03
 * @Description:
 */
@FeignClient("account")
public interface AccountCustomerOpenFeign {
    @PostMapping(value = "/account/transfer", produces = "application/json")
    Result transfer(@RequestBody UpdateMoneyVO updateMoney);
}
