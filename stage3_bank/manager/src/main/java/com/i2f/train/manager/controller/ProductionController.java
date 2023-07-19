package com.i2f.train.manager.controller;

import com.i2f.train.manager.service.ProductionService;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.Production;
import com.i2f.train.starter.model.queryMap.ProductionQueryMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 刘志亮
 * @date: 2022/4/2 9:14
 */

@RestController
@RequestMapping("manager/production")
public class ProductionController {
    @Resource
    ProductionService productionService;

    /**
     * 增加积分商品
     *
     * @param production
     * @return Result
     */
    @PostMapping("/add")
    public Result add(@RequestBody Production production) {
        return productionService.add(production);
    }

    /**
     * 根据id删除积分商品
     *
     * @param production
     * @return Production
     */
    @PostMapping("deleteById")
    Result deleteById(@RequestBody Production production) {
        return productionService.deleteById(production);
    }

    /**
     * 更新积分商品信息
     *
     * @param production
     * @return Result
     */
    @PostMapping("/update")
    public Result update(@RequestBody Production production) {
        return productionService.update(production);
    }

    /**
     * 模糊查询积分商品不支持全查询
     *
     * @param queryMap
     * @return Result
     */
    @PostMapping("selectBySomeFieldLike")
    public Result selectByAnyFieldLike(@RequestBody ProductionQueryMap queryMap) {
        return productionService.selectByAnyFieldLike(queryMap);
    }
}
