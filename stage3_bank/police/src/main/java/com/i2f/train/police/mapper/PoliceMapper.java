package com.i2f.train.police.mapper;

import com.i2f.train.starter.model.Police;

import org.apache.ibatis.annotations.Mapper;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-03-30 11:28
 **/
@Mapper
public interface PoliceMapper {

    /**
     *
     * 根据姓名和身份证号查询
     * @param police
     * @return
     */
    int findPersonOne(Police police);
}
