package com.i2f.train.manager.service;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.FinanceItem;
import com.i2f.train.starter.model.queryMap.FinanceQueryMap;

import java.util.List;

/**
 * @author luyuanxin
 * @create 2022/4/1 17:22
 */
public interface FinanceItemService {

    Result<List<FinanceItem>> queryAll();

    /**
     * 新增理财产品
     * @param finance
     * @return
     */
    Result addFinance(FinanceItem finance);

    /**
     * 删除理财产品
     * @param finance
     * @return
     */
    Result deleteFinance(FinanceItem finance);

    /**
     * 修改理财产品
     * @param finance
     * @return
     */
    Result updateFinance(FinanceItem finance);

    /**
     * 修改产品上下架
     * @param finance
     * @return
     */
    Result statusChange(FinanceItem finance);

    /**
     * 查询所有理财产品
     * @param queryMap
     * @return
     */
    Result selectList(FinanceQueryMap queryMap);

}
