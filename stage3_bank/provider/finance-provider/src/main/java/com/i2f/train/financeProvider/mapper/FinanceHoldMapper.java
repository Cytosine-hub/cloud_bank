package com.i2f.train.financeProvider.mapper;


import com.i2f.train.starter.model.FinanceHold;
import com.i2f.train.starter.model.vo.HoldRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: hjx
 * @date: 2022/3/30 13:42
 */
@Mapper
public interface FinanceHoldMapper {
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
    int insert(FinanceHold record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(FinanceHold record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    FinanceHold selectByPrimaryKey(String id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(FinanceHold record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(FinanceHold record);

    List<FinanceHold> selectFinanceHoldByUserId(String userId);
    FinanceHold selectHoldByUserId(String userId,String financeId);

    List<FinanceHold> selectFinanceHOldByIdAndFiId(@Param("userId") String userId, @Param("financeId") String financeId);

    List<FinanceHold> selectAllHold();

    List<FinanceHold> selectHoldByStatus();

    String  selectSteadyFinanceHoldMoney(@Param("userId") String userId);
    String  selectAggressiveFinanceHoldMoney(@Param("userId") String userId);
    String  selectFinanceHoldProfit(String userId);
    String selectFinanceHoldTotalProfit(String userId);
}