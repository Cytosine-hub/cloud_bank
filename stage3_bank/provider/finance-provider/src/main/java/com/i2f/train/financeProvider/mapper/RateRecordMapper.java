package com.i2f.train.financeProvider.mapper;

import com.i2f.train.starter.model.RateRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author: hjx
 * @date: 2022/3/30 13:42
 */
@Mapper
public interface RateRecordMapper {
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
    int insert(RateRecord record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(RateRecord record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    RateRecord selectByPrimaryKey(String id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(RateRecord record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(RateRecord record);

    /**
     * 某个理财产品的昨日收益
     * @param fiId
     * @param todayDate
     * @return
     */
    RateRecord selectByFIId(@Param("fiId") String fiId, @Param("todayDate") String todayDate);

    /**
     * 查某个理财产品近七天的利率
     * @param fiId
     * @return
     */
    List<RateRecord> selectRateRecordByFiId(String fiId);

}