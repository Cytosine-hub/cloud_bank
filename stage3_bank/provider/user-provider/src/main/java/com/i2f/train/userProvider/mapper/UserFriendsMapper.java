package com.i2f.train.userProvider.mapper;

import com.i2f.train.starter.model.UserFriends;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 13:44
 */
@Mapper
public interface UserFriendsMapper {
    /**
     * delete by primary key
     * @param record the record
     * @return deleteCount
     */
    int deleteByPrimaryKey(UserFriends record);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(UserFriends record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(UserFriends record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    UserFriends selectByPrimaryKey(String id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int update(UserFriends record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(UserFriends record);

    /**
     * 通过UserFriends中的某些字段进行精确查询
     * @param userFriends
     * @return object by any field
     */
    ArrayList<UserFriends> selectByAnyField(UserFriends userFriends);

    /**
     * 通过UserFriends中的某些字段进模糊查询
     * @param userFriends
     * @return object by any field like
     */
    ArrayList<UserFriends> selectByAnyFieldLike(UserFriends userFriends);

    /**
     * 通过用户id删除用户所有好友
     * @param userFriends
     * @return deleteCount
     */
    int deleteByUserId(UserFriends userFriends);
}