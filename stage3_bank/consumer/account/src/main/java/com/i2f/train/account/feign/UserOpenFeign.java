package com.i2f.train.account.feign;

import com.i2f.train.starter.model.Police;
import com.i2f.train.starter.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户开户前的用户状态检查（判断用户状态以及用户是否已开户）
 * @author Wqsh
 * @version 1.0
 * @date 2022/3/31 11:07
 */
@FeignClient("user-provider")
public interface UserOpenFeign {
    @PostMapping(value = "/selectById",produces = "application/json")
    User selectByPrimaryKey(@RequestParam String userId);
}
