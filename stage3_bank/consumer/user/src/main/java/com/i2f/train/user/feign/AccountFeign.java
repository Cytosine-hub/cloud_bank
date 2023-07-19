package com.i2f.train.user.feign;

import com.i2f.train.starter.model.Account;
import com.i2f.train.starter.model.vo.UserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: 仁
 * 2022/4/2/ 11:18
 */
@FeignClient("account-provider")
public interface AccountFeign {
    /**修改账户表支付密码*/
    @RequestMapping(value = "/upAccountPayPassword",produces = "application/json")
    int upAccountPayPassword(@RequestBody UserVo userVo);

    /**根据accountId查询账户信息*/
    @RequestMapping(value = "/selectAccountId",produces = "application/json")
    Account selectAccountId(@RequestParam String id);
}
