package com.i2f.train.production.controller;

import com.i2f.train.production.service.ProductionService;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.User;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 15:10
 */
@RestController
@RequestMapping("/production")
public class ProductionController {
    @Resource
    ProductionService productionService;

    /**
     * 积分抽奖
     * @return
     */
    @GetMapping("bonusPointDraw")
    public Result bonusPointDraw(String integral){
        return productionService.bonusPointDraw(integral);
    }

    /**
     * 积分兑换
     * @param map
     * @return
     */
    @PostMapping("pointConversion")
    public Result pointConversion(@RequestBody Map<String ,String> map){
        return productionService.pointConversion(map);
    }

    @GetMapping("/list")
    public Result getList(){
        return productionService.getList();
    }

    @GetMapping("/detail")
    public Result getDetail(String id){
        return productionService.getDetail(id);
    }
}
