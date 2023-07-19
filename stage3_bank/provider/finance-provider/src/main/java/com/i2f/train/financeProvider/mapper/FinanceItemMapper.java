package com.i2f.train.financeProvider.mapper;

import com.i2f.train.starter.model.FinanceItem;
import com.i2f.train.starter.model.queryMap.FinanceQueryMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: hjx
 * @date: 2022/3/30 13:42
 */
@Mapper
public interface FinanceItemMapper {
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
    int insert(FinanceItem record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(FinanceItem record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    FinanceItem selectByPrimaryKey(String id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(FinanceItem record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(FinanceItem record);

    /**
     * 根据基金风险等级查询基金产品
     * @param level
     * @return
     */
    List<FinanceItem> queryAllFinanceByLevel(String level);

    /**
     * 查询理财产品
     * @param queryMap
     * @return
     */
    List<FinanceItem> queryFinances(FinanceQueryMap queryMap);

    /**
     * 删除理财产品
     * @param id
     * @return
     */
    int deleteFinance(String id);

    /**
     * 修改理财产品上下架
     * @param isOnsale
     * @param id
     * @return
     */
    int updateIsOnsale(String isOnsale, String id);

    FinanceItem selectItemById(String id);

    List<FinanceItem> selectItem();
}