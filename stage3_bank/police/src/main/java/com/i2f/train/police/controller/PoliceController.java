package com.i2f.train.police.controller;

import com.i2f.train.police.service.PoliceService;
import com.i2f.train.starter.model.Police;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-03-30 11:28
 **/
@RestController
@RequestMapping
public class PoliceController {
    @Autowired
    PoliceService policeService;

    @PostMapping("/confirm")
    public int confirm(@RequestBody Police police){
        return policeService.confirmMessage(police);
    }

}
