package com.i2f.train.accountProvider.mapper;

import com.i2f.train.starter.model.Account;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 13:40
 */
@Mapper
public interface AccountMapper {
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
    int insert(Account record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(Account record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    Account selectByPrimaryKey(String id);

    /**
     * select by any field
     * @param account
     * @return ArrayList<Account>
     */
    ArrayList<Account> selectByAnyField(Account account);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Account record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(Account record);

    Account findByAccountId(String inAccountId);

    //更新 account中二类户的 money
    int updateMoney(@Param("id") String id, @Param("money") String money);

    /**修改账户表支付密码*/
    int upAccountPayPassword(@Param("accountId") String accountId,@Param("payPassword") String payPassword);

}