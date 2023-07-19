package com.i2f.train.manager.service.impl;

import com.i2f.train.manager.feign.ProductionOpenFeign;
import com.i2f.train.manager.service.ProductionService;
import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.Production;
import com.i2f.train.starter.model.queryMap.ProductionQueryMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 刘志亮
 * @date: 2022/4/2 9:13
 */
@Service
public class ProductionServiceImpl implements ProductionService {
    @Resource
    ProductionOpenFeign productionOpenFeign;

    /**
     * 增加积分商品
     *
     * @param production
     * @return Result
     */
    @Override
    public Result add(Production production) {
        return productionOpenFeign.add(production);
    }

    /**
     * 更新积分商品信息
     *
     * @param production
     * @return Result
     */
    @Override
    public Result update(Production production) {
        return productionOpenFeign.update(production);
    }

    /**
     * 精确查询积分商品支持全查询
     *
     * @param production
     * @return Result
     */
    @Override
    public Result<List<Production>> selectByAnyField(Production production) {
        return productionOpenFeign.selectByAnyField(production);
    }

    /**
     * 模糊查询积分商品不支持全查询
     *
     * @param queryMap
     * @return Result
     */
    @Override
    public Result selectByAnyFieldLike(ProductionQueryMap queryMap) {
        return productionOpenFeign.selectByAnyFieldLike(queryMap);
    }

    /**
     * 删除积分商品
     *
     * @param production
     * @return Result
     */
    @Override
    public Result deleteById(Production production) {
        return productionOpenFeign.delete(production);
    }
}
