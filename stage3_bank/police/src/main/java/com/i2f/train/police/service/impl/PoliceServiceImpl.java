package com.i2f.train.police.service.impl;

import com.i2f.train.police.mapper.PoliceMapper;
import com.i2f.train.police.service.PoliceService;
import com.i2f.train.starter.model.Police;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-03-30 11:32
 **/
@Service
public class PoliceServiceImpl implements PoliceService {

    @Autowired
    PoliceMapper policeMapper;


    @Override
    public int confirmMessage(Police police) {
        return policeMapper.findPersonOne(police);
    }

}
