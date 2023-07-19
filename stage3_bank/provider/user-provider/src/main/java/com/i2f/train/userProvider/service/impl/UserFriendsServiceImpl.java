package com.i2f.train.userProvider.service.impl;

import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.common.util.UUIDGenerator;
import com.i2f.train.starter.model.UserFriends;
import com.i2f.train.userProvider.mapper.UserFriendsMapper;
import com.i2f.train.userProvider.service.UserFriendsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author: 刘志亮
 * @date: 2022/4/6 9:32
 */
@Service
public class UserFriendsServiceImpl implements UserFriendsService {
    @Resource
    UserFriendsMapper userFriendsMapper;

    @Resource
    UUIDGenerator uuidGenerator;

    /**
     * 通过UserFriends中的某些字段进行精确查询
     * @param userFriends
     * @return Result
     */
    @Override
    public Result selectByAnyField(UserFriends userFriends) {
        ArrayList<UserFriends> userFriendsArrayList = userFriendsMapper.selectByAnyField(userFriends);
        return Result.success(userFriendsArrayList);
    }

    /**
     * 通过UserFriends中的某些字段进行模糊查询
     * @param userFriends
     * @return Result
     */
    @Override
    public Result selectByAnyFieldLike(UserFriends userFriends) {
        ArrayList<UserFriends> userFriendsArrayList = userFriendsMapper.selectByAnyFieldLike(userFriends);
        return Result.success(userFriendsArrayList);
    }

    /**
     * 增加用户好友
     * @param userFriends
     * @return Result
     */
    @Override
    public Result add(UserFriends userFriends) {
        String id = uuidGenerator.getId();
        userFriends.setId(id);
        int ok = userFriendsMapper.insertSelective(userFriends);
        if (ok == 0){
            return Result.error(CommonConstants.Existence.NONEXISTENCE_CODE, CommonConstants.Existence.NONEXISTENCE_MESSAGE);
        }
        return Result.success("");
    }

    /**
     * 删除用户好友
     * @param userFriends
     * @return Result
     */
    @Override
    public Result delete(UserFriends userFriends) {
        int ok = userFriendsMapper.deleteByPrimaryKey(userFriends);
        if (ok == 0){
            return Result.error(CommonConstants.Existence.NONEXISTENCE_CODE, CommonConstants.Existence.NONEXISTENCE_MESSAGE);
        }
        return Result.success("");
    }

    /**
     * 修改用户好友信息
     * @param userFriends
     * @return Result
     */
    @Override
    public Result update(UserFriends userFriends) {
        int ok = userFriendsMapper.update(userFriends);
        if (ok == 0){
            return Result.error(CommonConstants.Existence.NONEXISTENCE_CODE, CommonConstants.Existence.NONEXISTENCE_MESSAGE);
        }
        return Result.success("");
    }

    /**
     * 删除用户所有好友
     * @param userFriends
     * @return Result
     */
    @Override
    public Result deleteAll(UserFriends userFriends) {
        int ok = userFriendsMapper.deleteByUserId(userFriends);
        if (ok == 0){
            return Result.error(CommonConstants.Existence.NONEXISTENCE_CODE, CommonConstants.Existence.NONEXISTENCE_MESSAGE);
        }
        return Result.success("");
    }

}
