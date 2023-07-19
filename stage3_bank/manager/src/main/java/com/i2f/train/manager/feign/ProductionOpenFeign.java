package com.i2f.train.manager.feign;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.Production;
import com.i2f.train.starter.model.queryMap.ProductionQueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: 刘志亮
 * @date: 2022/4/2 9:05
 */
@FeignClient("production-provider")
public interface ProductionOpenFeign {
    /**
     * 增加积分商品
     *
     * @param production
     * @return Result
     */
    @PostMapping(value = "production/addProduction", produces = "application/json")
    Result add(@RequestBody Production production);

    /**
     * 更新积分商品信息
     *
     * @param production
     * @return Result
     */
    @PostMapping(value = "production/updateByPrimaryKeySelective", produces = "application/json")
    Result update(@RequestBody Production production);

    /**
     * 精确查询积分商品支持全查询
     *
     * @param production
     * @return Result
     */
    @PostMapping(value = "production/selectByAnyField", produces = "application/json")
    Result<List<Production>> selectByAnyField(@RequestBody Production production);

    /**
     * 模糊查询积分商品不支持全查询
     *
     * @param queryMap
     * @return Result
     */
    @PostMapping(value = "production/selectByAnyFieldLike", produces = "application/json")
    Result selectByAnyFieldLike(@RequestBody ProductionQueryMap queryMap);

    /**
     * 根据id删除积分商品
     *
     * @param production
     * @return Result
     */
    @PostMapping(value = "production/delete", produces = "application/json")
    Result delete(@RequestBody Production production);

}
