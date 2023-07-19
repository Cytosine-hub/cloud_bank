package com.i2f.train.manager.controller;

import com.i2f.train.manager.service.FinanceItemService;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.FinanceItem;
import com.i2f.train.starter.model.queryMap.FinanceQueryMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author luyuanxin
 * @create 2022/4/1 17:22
 */
@RestController
@RequestMapping("/financeItem")
public class FinanceItemController {
    @Resource
    FinanceItemService financeService;

    /**
     * 查询所有理财产品--支持模糊查询
     * @param queryMap
     * @return
     */
    @RequestMapping("/query")
    public Result selectList(@RequestBody FinanceQueryMap queryMap){
        return financeService.selectList(queryMap);
    }

    /**
     * 添加理财产品
     * */
    @PostMapping("/addFinance")
    public Result addFinance(@RequestBody FinanceItem finance){
        return financeService.addFinance(finance);
    }

    /**
     * 删除理财产品
     * */
    @PostMapping("/deleteFinance")
    public Result deleteFinance(@RequestBody FinanceItem finance){
        return financeService.deleteFinance(finance);
    }

    /**
     * 修改理财产品
     * */
    @PostMapping("/updateFinance")
    public Result updateFinance(@RequestBody FinanceItem finance){
        return financeService.updateFinance(finance);
    }

    /**
     * 修改产品上下架
     * */
    @PostMapping("statusChange")
    public Result statusChange(@RequestBody FinanceItem finance){
        return financeService.statusChange(finance);
    }
}
