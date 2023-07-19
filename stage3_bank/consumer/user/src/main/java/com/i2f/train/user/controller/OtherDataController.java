package com.i2f.train.user.controller;

import com.i2f.train.user.common.util.HttpUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-04-13 20:00
 **/
@RestController
@RequestMapping
public class OtherDataController {

    @RequestMapping("/fyData")
    public String getFyData(){
        return HttpUtil.getFyData();
    }
}
