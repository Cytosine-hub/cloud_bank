package com.i2f.train.manager.service.impl;

import com.i2f.train.manager.feign.FinanceOpenFeign;
import com.i2f.train.manager.service.FinanceItemService;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.FinanceItem;
import com.i2f.train.starter.model.queryMap.FinanceQueryMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author luyuanxin
 * @create 2022/4/1 17:25
 */
@Service
public class FinanceItemServiceImpl implements FinanceItemService {

    @Resource
    FinanceOpenFeign financeOpenFeign;

    @Override
    public Result<List<FinanceItem>> queryAll() {
        return financeOpenFeign.queryAll();
    }

    /**
     * 新增理财产品
     * @param finance
     * @return
     */
    @Override
    public Result addFinance(FinanceItem finance) {
        return financeOpenFeign.addFinance(finance);
    }

    /**
     * 删除理财产品
     * @param finance
     * @return
     */
    @Override
    public Result deleteFinance(FinanceItem finance) {
        return financeOpenFeign.deleteFinance(finance);
    }

    /**
     * 修改理财产品
     * @param finance
     * @return
     */
    @Override
    public Result updateFinance(FinanceItem finance) {
        return financeOpenFeign.updateFinance(finance);
    }

    /**
     * 修改产品上下架
     * @param finance
     * @return
     */
    @Override
    public Result statusChange(FinanceItem finance) {
        return financeOpenFeign.statusChange(finance);
    }

    /**
     * 查询所有理财产品
     * @param queryMap
     * @return
     */
    @Override
    public Result selectList(FinanceQueryMap queryMap) {
        return financeOpenFeign.queryFinances(queryMap);
    }
}
