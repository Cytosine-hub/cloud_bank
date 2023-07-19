package com.i2f.train.userProvider.mapper;

import com.i2f.train.starter.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 17:41
 */
@Mapper
public interface UserMapper {
    /**
     * delete by primary key
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(String id);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(User record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(User record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    User selectByPrimaryKey(String id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(User record);

    User findByPhone(String mobilePhone);

    int upPassword(@Param("userId")String userId, @Param("password")String password);

    int upPayPassword(@Param("userId")String userId,@Param("payPassword")String payPassword);

    int updateByUserId(@Param("username") String username,@Param("accountId") String accountId, @Param("personalId") String personalId, @Param("payPassword") String payPassword,  @Param("userId") String userId);

    /**
     * 删除开户信息
     *
     * @return int
     */
    int deleteUserAccountRelation(@Param("userId") String userId);

    /**
     * 删除用户所有好友
     *
     * @return int
     */
    int deleteAllUserFriend(@Param("userId") String userId);
}