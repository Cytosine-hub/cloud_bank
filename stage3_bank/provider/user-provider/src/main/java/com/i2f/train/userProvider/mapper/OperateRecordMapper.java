package com.i2f.train.userProvider.mapper;

import com.i2f.train.starter.model.OperateRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 13:43
 */
@Mapper
public interface OperateRecordMapper {
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
    int insert(OperateRecord record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    boolean insertSelective(OperateRecord record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    OperateRecord selectByPrimaryKey(String id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(OperateRecord record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(OperateRecord record);

    /**
     * 修改可疑记录
     * @param id
     * @param success
     * @return
     */
    boolean updateById(@Param("id") String id, @Param("success") String success);

    boolean addOpenAccountOperateRecord(@Param("id") String id, @Param("userId")String userId, @Param("type")String type, @Param("status")String status, @Param("time")Date time);
}