package com.i2f.train.financeProvider.mapper;

import com.i2f.train.starter.model.FinanceOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: hjx
 * @date: 2022/3/30 13:42
 */
@Mapper
public interface FinanceOrderMapper {
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
    int insert(FinanceOrder record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(FinanceOrder record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    FinanceOrder selectByPrimaryKey(String id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(FinanceOrder record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(FinanceOrder record);

    /**
     * 查询订单列表
     * @param userId
     * @param status
     * @return
     */
    List<FinanceOrder> getOrderList(@Param("userId") String userId,@Param("status") String status);
    /**
     * 查询赎回中的产品
     * @param status
     * @param type
     * @return
     */
    List<FinanceOrder> selectOrderByStatus(String status,String type);

    List<FinanceOrder> selectSubscribeOrderAndNotConfirm();

    FinanceOrder selectSaleOrderAndNotConfirm(@Param("userId") String userId, @Param("financeId") String financeId);
}