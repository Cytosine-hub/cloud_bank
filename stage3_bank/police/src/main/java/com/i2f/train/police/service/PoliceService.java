package com.i2f.train.police.service;

import com.i2f.train.starter.model.Police;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-03-30 11:28
 **/
public interface PoliceService {
    /**
     * 根据姓名和身份证号查询用户信息是否正确
     * @param police
     * @return
     */
    int confirmMessage(Police police);
}
