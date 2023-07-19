package com.i2f.train.manager.mapper;

import com.i2f.train.manager.model.Manager;
import com.i2f.train.manager.model.dto.ManagerDto;
import com.i2f.train.manager.model.queryMap.ManagerQueryMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 13:42
 */
@Mapper
public interface ManagerMapper {
    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(String id);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(Manager record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(Manager record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    Manager selectByPrimaryKey(String id);

    /**
     * 根据手机号查询管理员信息
     * @param mobilePhone 手机号
     * @return
     */
    Manager findByPhone(String mobilePhone);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Manager record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(Manager record);

    /**
     * 根据id修改level
     * @param level
     * @param id
     * @return
     */
    int updateLevelById(String level,String id);

    /**
     * 查询管理员列表
     * @param queryMap
     * @return
     */
    List<ManagerDto> queryManagers(ManagerQueryMap queryMap);
}