package com.i2f.train.finance.feign;

import com.i2f.train.starter.model.OperateRecord;
import com.i2f.train.starter.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/01/14:36
 * @Description:
 */
@FeignClient("user-provider")
public interface UserOpenFeign {
    @PostMapping(value = "/queryUser", produces = "application/json")
    User selectUserById(@RequestBody String userId);

    @PostMapping(value = "/operateRecord/operateRecordAdd", produces = "application/json")
    boolean operateRecordAdd(@RequestBody OperateRecord operateRecord);

    @PostMapping(value = "/operateRecord/updateOperate", produces = "application/json")
    boolean updateOperate(@RequestBody OperateRecord operateRecord);
}
