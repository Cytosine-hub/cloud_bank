package com.i2f.train.user.controller;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.UserFriends;
import com.i2f.train.user.service.UserFriendsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 刘志亮
 * @date: 2022/4/6 10:34
 */
@RestController
@RequestMapping("/userFriends")
public class UserFriendsController {
    @Resource
    UserFriendsService userFriendsService;

    /**
     * 通过UserFriends中的某些字段进行精确查询
     * @return Result
     */
    @GetMapping("/selectByAnyField")
    public Result selectByAnyField() {
        return userFriendsService.selectByAnyField();
    }

    /**
     * 通过UserFriends中的某些字段进行模糊查询
     * @param userFriends
     * @return Result
     */
    @PostMapping("/selectByAnyFieldLike")
    public Result selectByAnyFieldLike(@RequestBody UserFriends userFriends) {
        return userFriendsService.selectByAnyFieldLike(userFriends);
    }

    /**
     * 增加用户好友
     * @param userFriends
     * @return Result
     */
    @PostMapping("/add")
    public Result add(@RequestBody UserFriends userFriends) {
        return userFriendsService.add(userFriends);
    }

    /**
     * 删除用户好友
     * @param userFriends
     * @return Result
     */
    @PostMapping("/delete")
    public Result delete(@RequestBody UserFriends userFriends) {
        return userFriendsService.delete(userFriends);
    }

    /**
     * 删除用户好友
     * @param userFriends
     * @return Result
     */
    @PostMapping("/deleteAll")
    public Result deleteAll(@RequestBody UserFriends userFriends) {
        return userFriendsService.deleteAll(userFriends);
    }

    /**
     * 修改用户好友信息
     * @param userFriends
     * @return Result
     */
    @PostMapping("/update")
    public Result update(@RequestBody UserFriends userFriends) {
        return userFriendsService.update(userFriends);
    }
}
