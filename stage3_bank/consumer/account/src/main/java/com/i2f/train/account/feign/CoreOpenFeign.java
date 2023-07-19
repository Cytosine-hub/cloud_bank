package com.i2f.train.account.feign;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.vo.CheckAccountVO;
import com.i2f.train.starter.model.vo.OpenAccountVo;
import com.i2f.train.starter.model.Account;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-03-30 21:33
 **/
@FeignClient("core")
public interface CoreOpenFeign {
    /**
     * 开户
     * @param openAccountVo
     * @param accountId
     * @return
     */

    @PostMapping(value = "/core/open",produces = "application/json")
    Result openAccount(@RequestBody OpenAccountVo openAccountVo,@RequestParam String accountId);

    /**
     * 去访问核心验证预留手机号、身份证和银行卡
     * @param checkAccount
     * @return Result
     */
    @PostMapping(value = "core/bindCard", produces = "application/json")
    Result checkBindCardMessage(@RequestBody CheckAccountVO checkAccount);

    /**
     * 向核心发起更改用户余额变动请求
     * @param updateMoney
     * @return Result
     */
    @PostMapping(value = "core/updateMoney", produces = "application/json")
    Result updateMoney(@RequestBody UpdateMoneyVO updateMoney);

    /**
     * 向核心发起查询银行卡信息
     * @param accountId
     * @return Result
     */
    @GetMapping(value = "core/selectAccountId",produces = "application/json")
    Account selectByPrimaryKey(@RequestParam String accountId);

}
