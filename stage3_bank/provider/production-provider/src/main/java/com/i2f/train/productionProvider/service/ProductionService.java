package com.i2f.train.productionProvider.service;/*
WDNG
 三月31号17:15
*/

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.Production;
import com.i2f.train.starter.model.queryMap.ProductionQueryMap;

import java.util.List;

public interface ProductionService {
    /**
     * 根据产品id查找产品信息
     *
     * @param productionId
     * @return
     */
    Production selectProduction(String productionId);

    /**
     * 更新积分商品信息
     *
     * @param production
     * @return
     */
    Result updateByPrimaryKeySelective(Production production);

    /**
     * 增加积分商品
     *
     * @param production
     * @return Result
     */
    Result add(Production production);

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
