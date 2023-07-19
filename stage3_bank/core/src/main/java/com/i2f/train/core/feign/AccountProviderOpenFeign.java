package com.i2f.train.core.feign;

import com.i2f.train.starter.model.Account;
import com.i2f.train.starter.model.vo.CheckAccountVO;
import com.i2f.train.starter.model.vo.OpenAccountVo;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-03-30 22:00
 **/
@FeignClient("account-provider")
public interface AccountProviderOpenFeign {
    /**
     * 前往开户的provider
     * @param openAccountVo
     * @param accountId
     * @return
     */

    @PostMapping(value = "/insert", produces = "application/json")
    int openAccount(@RequestBody OpenAccountVo openAccountVo,@RequestParam String accountId);

    /**
     * 验证绑卡信息是否正确
     * @param checkAccount
     * @return
     */
    @PostMapping(value = "/bindCar", produces = "application/json")
    Result bindCar(@RequestBody CheckAccountVO checkAccount);

    /**
     * 更改用户余额
     * @param updateMoney
     * @return Result
     */
    @PostMapping(value = "updateMoney", produces = "application/json")
    Result updateMoney(@RequestBody UpdateMoneyVO updateMoney);

    @PostMapping(value = "/transfer", produces = "application/json")
    Result transfer(@RequestBody Map<String, Object> map);

    /**
     * 通过银行卡id查询银行卡
     * @param id
     * @return
     */
    @GetMapping(value = "/selectAccountId", produces = "application/json")
    Account selectByPrimaryKey(@RequestParam String id);
}
