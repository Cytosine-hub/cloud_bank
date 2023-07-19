package com.i2f.train.account.feign;

import com.i2f.train.starter.model.Police;
import com.i2f.train.starter.model.vo.OpenAccountVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-03-30 21:32
 **/
@FeignClient("police")
public interface PoliceOpenFeign {

    @PostMapping(value = "/confirm",produces = "application/json")
    int confirm(Police police);
}
