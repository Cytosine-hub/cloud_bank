package com.i2f.train.user.feign;

import com.i2f.train.starter.model.vo.FinanceHoldVO;
import com.i2f.train.starter.model.vo.FinanceOrderVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: 仁
 * 2022/4/13/ 14:46
 */
@FeignClient("finance-provider")
public interface FinanceFeign {
    /**
     * 获取用户持仓列表
     * @param userId
     * @return
     */
    @PostMapping(value = "/selectFinanceHold", produces = "application/json")
    List<FinanceHoldVO> selectFinanceHold(@RequestParam String userId);

    /**
     * 获取用户正在处理中的订单
     * @param userId
     * @return
     */
    @PostMapping(value = "/selectFinanceOrder", produces = "application/json")
    List<FinanceOrderVO> getOrder(@RequestParam String status,@RequestParam String userId);
}
