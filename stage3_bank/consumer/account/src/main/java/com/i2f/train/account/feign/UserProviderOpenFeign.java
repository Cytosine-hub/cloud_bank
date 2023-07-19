package com.i2f.train.account.feign;

import com.i2f.train.starter.model.OperateRecord;
import com.i2f.train.starter.model.User;
import com.i2f.train.starter.model.UserAccountRelation;
import com.i2f.train.starter.model.vo.OpenAccountVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Map;


/**
 * @author: 刘志亮
 * @date: 2022/3/31 9:18
 */
@FeignClient("user-provider")
public interface UserProviderOpenFeign {
    /**
     * 通过用户Id去数据库查询用户信息
     * @param userId
     * @return User
     */
    @PostMapping(value = "selectById", produces = "application/json")
    User selectByUserId(@RequestParam String userId);

    /**
     * 增加操作记录
     * @param operateRecord
     * @return Result
     */
    @PostMapping(value = "operateRecord/operateRecordAdd", produces = "application/json")
    boolean operateRecordAdd(@RequestBody OperateRecord operateRecord);

    /**
     * 通过用户Id和银行卡Id查询用户是否已经绑定该银行卡
     *
     * @param accountId
     * @param userId
     * @return Result
     */
    @PostMapping(value = "userAccountRelation/selectByAccountIdAndUserId", produces = "application/json")
    ArrayList<UserAccountRelation> selectByAccountIdAndUserId(@RequestParam String accountId, @RequestParam String userId);

    /**
     * 绑定该银行卡
     *
     * @param userAccountRelation
     * @return boolean
     */
    @PostMapping(value = "userAccountRelation/bindCard", produces = "application/json")
    boolean bindCard(@RequestBody UserAccountRelation userAccountRelation);

    /**
     * 更新用户可开户相关的信息
     *
     * @param openAccountVo
     * @param userId
     * @param accountId
     * @return boolean
     */
    @PostMapping(value = "updateUser", produces = "application/json")
    boolean updateUser(@RequestBody OpenAccountVo openAccountVo, @RequestParam String userId, @RequestParam String accountId);

    /**
     * 将开户的可以记录修改成成功
     *
     * @param id
     * @param success
     * @return
     */

    @RequestMapping(value = "operateRecord/updateOperateRecord", produces = "application/json")
    boolean updateOperateRecord(@RequestParam String id, @RequestParam String success);
}
