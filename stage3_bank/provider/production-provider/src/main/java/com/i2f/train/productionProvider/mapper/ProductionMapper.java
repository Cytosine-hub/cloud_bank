package com.i2f.train.productionProvider.mapper;

import com.i2f.train.starter.model.Production;
import com.i2f.train.starter.model.queryMap.ProductionQueryMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface ProductionMapper {
    int deleteByPrimaryKey(String id);

    int insert(Production record);

    int insertSelective(Production record);

    Production selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Production record);

    int updateByPrimaryKey(Production record);

    /**
     * 通过某些字段模糊查询信息，不可全查询
     * @param production
     * @return ArrayList<Production>
     */
    ArrayList<Production> selectByAnyFieldLike(Production production);

    /**
     * 通过某些字段精确查询信息，可全查询
     * @param record
     * @return
     */
    ArrayList<Production> selectByAnyField(Production record);
}