package com.i2f.train.userProvider.controller;

import com.i2f.train.starter.model.User;
import com.i2f.train.starter.model.vo.UserVo;
import com.i2f.train.starter.model.vo.OpenAccountVo;
import com.i2f.train.userProvider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 13:56
 */
@RestController
@RequestMapping
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/queryUser")
    User selectUserById(@RequestBody String userId) {
        User user = userService.selectUserById(userId);
        return user;
    }

    /**
     * 通过用户Id查询用户信息
     * @param userId
     * @return User
     */
    @RequestMapping("/selectById")
    public User selectByUserId(@RequestParam String userId) {
        return userService.selectById(userId);
    }

    /**
     * 修改用户信息
     * @param user
     * @return boolean
     */
    public boolean update(User user) {
        return userService.update(user);
    }
    /**通过手机号查找用户信息*/
    @RequestMapping("/findByPhone")
    public User findByPhone(String mobilePhone){
        User user = userService.findByPhone(mobilePhone);
        return user;
    }

    /**用户注册，增加新用户*/
    @RequestMapping("/addUser")
    public int addUser(@RequestBody UserVo userVo){
        int result=userService.addUser(userVo);
        return result;
    }

    /**
     * 更新用户开户的相关信息
     * @param openAccountVo
     * @return
     */
    @PostMapping("/updateUser")
    public boolean updateUser(@RequestBody OpenAccountVo openAccountVo, @RequestParam String userId, @RequestParam String accountId){
        boolean flag = userService.updateByUserId(openAccountVo, userId, accountId);
        return flag;
    }

    /**
     * 通过用户id修改用户部分信息
     * @param user
     * @return
     */
    @PostMapping("updateByPrimaryKeySelective")
    public int updateByPrimaryKeySelective(@RequestBody User user) {
        return userService.updateByPrimaryKeySelective(user);

    }

    /**密码登录，校验用户信息*/
    @RequestMapping("/judgeUserInfo")
    public User judgeUserInfo(@RequestBody UserVo userVo){
        User loginUser=userService.judgeUserInfo(userVo);
        return loginUser;
    }

    /**修改手机号*/
    @RequestMapping("/upPhone")
    public int upPhone(@RequestBody UserVo userVo){
        int ok=userService.upPhone(userVo);
        return ok;
    }

    /**修改登录密码*/
    @RequestMapping("/upPassword")
    public int upPassword(@RequestBody UserVo userVo){
        int ok=userService.upPassword(userVo);
        return ok;
    }

    /**修改用户表支付密码*/
    @RequestMapping("/upPayPassword")
    public int upPayPassword(@RequestBody UserVo userVo){
        int ok=userService.upPayPassword(userVo);
        return ok;
    }

    /**删除用户信息*/
    @RequestMapping("/deleteUser")
    public int deleteUser(@RequestParam String userId){
        return userService.deleteUser(userId);
    }

    /**删除开户信息*/
    @RequestMapping("/deleteUserAccountRelation")
    public int deleteUserAccountRelation(@RequestParam String userId){
        return userService.deleteUserAccountRelation(userId);
    }

    /**删除用户所有好友*/
    @RequestMapping("/deleteAllUserFriend")
    public int deleteAllUserFriend(@RequestParam String userId){
        return userService.deleteAllUserFriend(userId);
    }
}
