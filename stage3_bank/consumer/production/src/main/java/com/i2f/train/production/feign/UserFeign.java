package com.i2f.train.production.feign;

import com.i2f.train.starter.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: 仁
 * 2022/3/30/ 15:09
 */
@FeignClient("user-provider")
public interface UserFeign {

    /**
     * 通过用户Id去查询用户信息
     * @param userId
     * @return User
     */
    @RequestMapping(value = "selectById", produces = "application/json")
    User selectByPrimaryKey(@RequestParam String userId);

    /**
     * 修改用户部分信息
     * @param user
     * @return
     */
    @PostMapping(value = "updateByPrimaryKeySelective", produces = "application/json")
    int updateByPrimaryKeySelective(@RequestBody User user);

}
