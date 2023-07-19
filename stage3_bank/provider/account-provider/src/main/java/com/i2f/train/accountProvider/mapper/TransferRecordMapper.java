package com.i2f.train.accountProvider.mapper;

import com.i2f.train.starter.model.TransferRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 13:43
 */
@Mapper
public interface TransferRecordMapper {
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
    int insert(TransferRecord record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(TransferRecord record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    TransferRecord selectByPrimaryKey(String id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(TransferRecord record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(TransferRecord record);


    List<TransferRecord> selectByConditions(@Param(value = "inOrOut") String inOrOut, @Param(value = "month") String month, @Param(value = "card") String card, @Param(value = "type") String type, @Param(value = "startTime") Timestamp startTime, @Param(value = "endTime") Timestamp endTime);

    /**
     * 获取当日交易记录
     * @param now
     * @param outAccountId
     * @return
     */
    List<TransferRecord> getDailyRecord(@Param("now") String now,@Param("outAccountId") String outAccountId);
}