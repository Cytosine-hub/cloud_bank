package com.i2f.train.account.feign;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.Account;
import com.i2f.train.starter.model.TransferRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/03/30/17:52
 * @Description:
 */
@FeignClient(name = "account-provider")
public interface AccountOpenFeign {
    @PostMapping(value = "/queryTransfer",produces = "application/json")
    List<TransferRecord> queryTransfer(@RequestParam("inOrOut") String inOrOut,@RequestParam("month") String month, @RequestParam("card") String card, @RequestParam("type") String type, @RequestParam("startTime") Timestamp startTime, @RequestParam("endTime") Timestamp endTime);

    @PostMapping(value = "/queryTransferDetail",produces = "application/json")
    TransferRecord queryTransferDetail(@RequestParam("id") String id);

    @PostMapping(value = "/queryAccount",produces = "application/json")
    Account queryAccount(@RequestParam("id") String id);

    /**
     * 增加一条转账记录
     * @param transferRecord
     * @return
     */
    @PostMapping(value = "/addTransferRecord",produces = "application/json")
    Result add(@RequestBody TransferRecord transferRecord);
}
