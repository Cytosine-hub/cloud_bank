package com.i2f.train.account.feign;

import com.i2f.train.starter.model.UserAccountRelation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/03/30/23:23
 * @Description:
 */
@FeignClient(name = "user-provider")
public interface UserAccountRelationOpenFeign {
    @PostMapping(value = "userAccountRelation/queryAccounts",produces = "application/json")
    List<UserAccountRelation> selectAccountByUserId(@RequestParam("userId") String userId);
}
