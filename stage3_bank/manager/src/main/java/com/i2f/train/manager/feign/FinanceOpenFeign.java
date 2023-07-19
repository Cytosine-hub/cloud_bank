package com.i2f.train.manager.feign;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.FinanceItem;
import com.i2f.train.starter.model.queryMap.FinanceQueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author luyuanxin
 * @create 2022/4/2 17:30
 */
@FeignClient("finance-provider")
public interface FinanceOpenFeign {


    @GetMapping(value = "queryAll",produces = "application/json")
    Result<List<FinanceItem>> queryAll();

   /**
     * 增加理财商品
     *
     * @param finance
     * @return Result
     */
    @PostMapping(value = "addFinance", produces = "application/json")
    Result addFinance(@RequestBody FinanceItem finance);

    /**
     * 查询理财商品
     *
     * @param queryMap
     * @return Result
     */
    @RequestMapping(value = "queryFinances", produces = "application/json")
    Result queryFinances(@RequestBody FinanceQueryMap queryMap);

 /**
  * 删除理财产品
  *
  * @param finance
  * @return Result
  */
   @PostMapping(value = "deleteFinance", produces = "application/json")
     Result deleteFinance(@RequestBody FinanceItem finance);

    /**
     * 修改理财产品
     *
     * @param finance
     * @return Result
     */
    @PostMapping(value = "updateFinance", produces = "application/json")
    Result updateFinance(@RequestBody FinanceItem finance);

    /**
     * 修改理财产品上下架
     *
     * @param finance
     * @return Result
     */
    @PostMapping(value = "statusChange", produces = "application/json")
    Result statusChange(@RequestBody FinanceItem finance);
}
