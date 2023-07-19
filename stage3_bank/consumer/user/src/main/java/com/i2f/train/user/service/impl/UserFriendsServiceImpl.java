package com.i2f.train.user.service.impl;

import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.common.util.JwtUtils;
import com.i2f.train.starter.model.UserFriends;
import com.i2f.train.user.feign.UserFeign;
import com.i2f.train.user.service.UserFriendsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 刘志亮
 * @date: 2022/4/6 10:28
 */
@Service
public class UserFriendsServiceImpl implements UserFriendsService {
    @Resource
    UserFeign userFeign;

    /**
     * 通过UserFriends中的某些字段进行精确查询
     * @return Result
     */
    @Override
    public Result selectByAnyField() {
        //从token中获取用户ID
        UserFriends userFriends = new UserFriends();
        String userId = JwtUtils.getUserId();
        userFriends.setUserId(userId);
        return userFeign.selectByAnyField(userFriends);
    }

    /**
     * 通过UserFriends中的某些字段进模糊查询
     * @param userFriends
     * @return Result
     */
    @Override
    public Result selectByAnyFieldLike(UserFriends userFriends) {
        //从token中获取用户ID
        String userId = JwtUtils.getUserId();
        userFriends.setUserId(userId);
        return userFeign.selectByAnyFieldLike(userFriends);
    }

    /**
     * 增加用户好友
     * @param userFriends
     * @return Result
     */
    @Override
    public Result add(UserFriends userFriends) {
        //从token中获取用户ID
        String userId = JwtUtils.getUserId();
        userFriends.setUserId(userId);
        Result result = userFeign.selectByAnyFieldLike(userFriends);
        ArrayList data = (ArrayList) result.getData();
        if (data == null || !data.isEmpty()){
            return Result.error(CommonConstants.Request.FAILED, "该联系人已存在！");
        }
        return userFeign.add(userFriends);
    }

    /**
     * 删除用户好友
     * @param userFriends
     * @return Result
     */
    @Override
    public Result delete(UserFriends userFriends) {
        //从token中获取用户ID
        String userId = JwtUtils.getUserId();
        userFriends.setUserId(userId);
        return userFeign.delete(userFriends);
    }

    /**
     * 删除用户所有好友
     * @param userFriends
     * @return Result
     */
    @Override
    public Result deleteAll(UserFriends userFriends) {
        //从token中获取用户ID
        String userId = JwtUtils.getUserId();
        userFriends.setUserId(userId);
        return userFeign.deleteAll(userFriends);
    }

    /**
     * 修改用户好友信息
     * @param userFriends
     * @return Result
     */
    @Override
    public Result update(UserFriends userFriends) {
        //从token中获取用户ID
        String userId = JwtUtils.getUserId();
        userFriends.setUserId(userId);
        return userFeign.update(userFriends);
    }
}
