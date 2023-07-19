package com.i2f.train.user.feign;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.User;
import com.i2f.train.starter.model.UserAccountRelation;
import com.i2f.train.starter.model.vo.UserVo;
import com.i2f.train.starter.model.UserFriends;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: 仁
 * 2022/3/30/ 15:09
 */
@FeignClient("user-provider")
public interface UserFeign {
    /**通过手机号查找用户信息*/
    @RequestMapping(value = "/findByPhone",produces = "application/json")
    User findByPhone(@RequestParam String mobilePhone);

    /**用户注册*/
    @RequestMapping(value = "/addUser",produces = "application/json")
    int addUser(@RequestBody UserVo userVo);

    /**密码登录，校验用户信息*/
    @RequestMapping(value = "/judgeUserInfo",produces = "application/json")
    User judgeUserInfo(@RequestBody UserVo userVo);

    /**修改手机号*/
    @RequestMapping(value = "/upPhone",produces = "application/json")
    int upPhone(@RequestBody UserVo userVo);

    /**修改登录密码*/
    @RequestMapping(value = "/upPassword",produces = "application/json")
    int upPassword(@RequestBody UserVo userVo);

    /**修改用户表支付密码*/
    @RequestMapping(value = "/upPayPassword",produces = "application/json")
    int upPayPassword(@RequestBody UserVo userVo);

    /**删除用户信息*/
    @RequestMapping(value = "/deleteUser", produces = "application/json")
    int deleteUser(@RequestParam String userId);

    /**删除开户信息*/
    @RequestMapping(value = "/deleteUserAccountRelation", produces = "application/json")
    int deleteUserAccountRelation(@RequestParam String userId);

    /**删除用户所有好友*/
    @RequestMapping(value = "/deleteAllUserFriend", produces = "application/json")
    int deleteAllUserFriend(@RequestParam String userId);

    /**
     * 通过用户Id去查询用户信息
     * @param userId
     * @return User
     */
    @RequestMapping(value = "/selectById", produces = "application/json")
    User selectByPrimaryKey(@RequestParam String userId);

    /**
     * 修改用户部分信息
     * @param user
     * @return
     */
    @PostMapping(value = "updateByPrimaryKeySelective", produces = "application/json")
    int updateByPrimaryKeySelective(@RequestBody User user);
    //User selectById(@RequestParam String id);

    /**
     * 通过UserFriends中的某些字段进行精确查询
     * @param userFriends
     * @return Result
     */
    @PostMapping(value = "UserFriends-provider/selectByAnyField", produces = "application/json")
    Result selectByAnyField(@RequestBody UserFriends userFriends);

    /**
     * 通过UserFriends中的某些字段进行模糊查询
     * @param userFriends
     * @return Result
     */
    @PostMapping(value = "UserFriends-provider/selectByAnyFieldLike", produces = "application/json")
    Result selectByAnyFieldLike(@RequestBody UserFriends userFriends);

    /**
     * 增加用户好友
     * @param userFriends
     * @return Result
     */
    @PostMapping(value = "UserFriends-provider/add", produces = "application/json")
    Result add(@RequestBody UserFriends userFriends);

    /**
     * 删除用户好友
     * @param userFriends
     * @return Result
     */
    @PostMapping(value = "UserFriends-provider/delete", produces = "application/json")
    Result delete(@RequestBody UserFriends userFriends);

    /**
     * 删除用户好友
     * @param userFriends
     * @return Result
     */
    @PostMapping(value = "UserFriends-provider/deleteAll", produces = "application/json")
    Result deleteAll(@RequestBody UserFriends userFriends);

    /**
     * 修改用户好友信息
     * @param userFriends
     * @return Result
     */
    @PostMapping(value = "UserFriends-provider/update", produces = "application/json")
    Result update(@RequestBody UserFriends userFriends);

    /**
     * 解除银行卡绑定
     * @param accountId
     * @param userId
     * @return boolean
     */
    @PostMapping(value = "userAccountRelation/removeCard", produces = "application/json")
    Result removeCard(@RequestParam String accountId,@RequestParam String userId);

    /**
     * 解除全部银行卡绑定
     * @param userId
     * @return boolean
     */
    @PostMapping(value = "userAccountRelation/removeAllCard", produces = "application/json")
    Result removeAllCard(@RequestParam String userId);

    /**
     * 解除全部银行卡绑定
     * @param userId
     * @return boolean
     */
    @GetMapping(value = "userAccountRelation/queryAccounts", produces = "application/json")
    List<UserAccountRelation> getRelations(@RequestParam String userId);

    @PostMapping(value = "/userAccountRelation/selectByAccountAndId", produces = "application/json")
    UserAccountRelation selectByAccountAndId(@RequestParam String userId, @RequestParam String accountId);
}
