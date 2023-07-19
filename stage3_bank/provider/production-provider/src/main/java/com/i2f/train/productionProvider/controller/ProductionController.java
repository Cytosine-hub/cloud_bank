package com.i2f.train.productionProvider.controller;/*
WDNG
 三月31号17:14
*/

import com.i2f.train.productionProvider.service.ProductionService;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.Production;
import com.i2f.train.starter.model.User;
import com.i2f.train.starter.model.queryMap.ProductionQueryMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("production")
public class ProductionController {
    @Autowired
    private  ProductionService productionService;

    /**
     * 根据产品id查找产品信息
     * @param productionId
     * @return Production
     */
    @PostMapping("selectProduction")
    Production selectProduction(@RequestBody String productionId) {
        return productionService.selectProduction(productionId);
    }

    /**
     * 根据id删除积分商品
     * @param production
     * @return Production
     */
    @PostMapping("delete")
    Result deleteById(@RequestBody Production production) {
        return productionService.deleteById(production);
    }

    /**
     *  更新积分商品信息
     * @param production
     * @return Result
     */
    @PostMapping("updateByPrimaryKeySelective")
    Result updateByPrimaryKeySelective(@RequestBody Production production){
        return productionService.updateByPrimaryKeySelective(production);
    }

    /**
     * 增加积分商品
     *
     * @param production
     * @return Result
     */
    @PostMapping("addProduction")
    public Result add(@RequestBody Production production) {
        return productionService.add(production);
    }

    /**
     * 精确查询积分商品支持全查询
     *
     * @param production
     * @return Result
     */
    @PostMapping("selectByAnyField")
    public Result<List<Production>> selectByAnyField(@RequestBody Production production) {
        return productionService.selectByAnyField(production);
    }

    /**
     * 模糊查询积分商品不支持全查询
     *
     * @param queryMap
     * @return Result
     */
    @PostMapping("selectByAnyFieldLike")
    public Result selectByAnyFieldLike(@RequestBody ProductionQueryMap queryMap) {
        return productionService.selectByAnyFieldLike(queryMap);
    }
}
