package com.i2f.train.userProvider.service;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.UserFriends;


/**
 * @author: 刘志亮
 * @date: 2022/4/6 9:32
 */
public interface UserFriendsService {
    /**
     * 通过UserFriends中的某些字段进行精确查询
     * @param userFriends
     * @return Result
     */
    Result selectByAnyField(UserFriends userFriends);

    /**
     * 通过UserFriends中的某些字段进模糊查询
     * @param userFriends
     * @return Result
     */
    Result selectByAnyFieldLike(UserFriends userFriends);

    /**
     * 增加用户好友
     * @param userFriends
     * @return Result
     */
    Result add(UserFriends userFriends);

    /**
     * 删除用户好友
     * @param userFriends
     * @return Result
     */
    Result delete(UserFriends userFriends);

    /**
     * 修改用户好友信息
     * @param userFriends
     * @return Result
     */
    Result update(UserFriends userFriends);

    /**
     * 删除用户所有好友
     * @param userFriends
     * @return Result
     */
    Result deleteAll(UserFriends userFriends);
}
