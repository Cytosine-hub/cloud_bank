package com.i2f.train.userProvider.mapper;

import com.i2f.train.starter.model.UserAccountRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 13:44
 */
@Mapper
public interface UserAccountRelationMapper {
    /**
     * delete by primary key
     * @param accountId primaryKey
     * @param userId
     * @return deleteCount
     */
    int deleteByPrimaryKey(@Param("accountId") String accountId,@Param("userId") String userId);

    /**
     * delete by userId
     * @param userId
     * @return deleteCount
     */
    int deleteByUserId(String userId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(UserAccountRelation record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(UserAccountRelation record);

    /**
     * select by primary key
     * @param accountId primary key
     * @return object by primary key
     */
    UserAccountRelation selectByPrimaryKey(String accountId);

    /**
     * select by any field
     * @param userAccountRelation
     * @return
     */
    ArrayList<UserAccountRelation> selectByAnyField(UserAccountRelation userAccountRelation);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(UserAccountRelation record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(UserAccountRelation record);

    List<UserAccountRelation> selectAccountByUserId(String userId);

    UserAccountRelation selectByAccountAndId(@Param(value = "userId") String userId, @Param(value = "accountId") String accountId);
}