package com.i2f.train.manager.service;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.Production;
import com.i2f.train.starter.model.queryMap.ProductionQueryMap;

import java.util.List;

/**
 * @author: 刘志亮
 * @date: 2022/4/2 9:13
 */
public interface ProductionService {
    /**
     * 增加积分商品
     *
     * @param production
     * @return Result
     */
    Result add(Production production);

    /**
     * 更新积分商品信息
     * @param production
     * @return Result
     */
    Result update(Production production);

    /**
     * 精确查询积分商品
     *
     * @param production
     * @return Result
     */
    Result<List<Production>> selectByAnyField(Production production);

    /**
     * 模糊查询积分商品
     *
     * @param queryMap
     * @return Result
     */
    Result selectByAnyFieldLike(ProductionQueryMap queryMap);

    /**
     * 删除积分商品
     *
     * @param production
     * @return Result
     */
    Result deleteById(Production production);
}
