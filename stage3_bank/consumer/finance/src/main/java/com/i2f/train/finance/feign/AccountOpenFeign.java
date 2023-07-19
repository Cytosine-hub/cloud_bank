package com.i2f.train.finance.feign;

import com.i2f.train.starter.model.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/01/19:15
 * @Description:
 */
@FeignClient("account-provider")
public interface AccountOpenFeign {
    @PostMapping(value = "/queryAccount", produces = "application/json")
    Account queryAccount(@RequestParam String id);
}
