package com.i2f.train.production.feign;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.Production;
import com.i2f.train.starter.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: 仁
 * 2022/3/30/ 15:09
 */
@FeignClient("production-provider")
public interface ProductionFeign {
    /**
     * 通过产品Id去查询产品信息
     * @param ProductionId
     * @return User
     */
    @PostMapping(value = "production/selectProduction", produces = "application/json")
    Production selectByPrimaryKey(@RequestBody String ProductionId);

    /**
     * 修改积分商品信息
     * @param production
     * @return
     */
    @PostMapping(value = "production/updateByPrimaryKeySelective", produces = "application/json")
    Result updateByPrimaryKeySelective(@RequestBody Production production);

    @PostMapping(value = "production/selectByAnyField", produces = "application/json")
    Result getProductions(@RequestBody Production production);
}
