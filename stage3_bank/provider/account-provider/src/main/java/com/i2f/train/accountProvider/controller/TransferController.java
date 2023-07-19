package com.i2f.train.accountProvider.controller;

import com.i2f.train.accountProvider.mapper.TransferRecordMapper;
import com.i2f.train.accountProvider.service.TransferService;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.TransferRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/03/30/18:15
 * @Description:
 */
@RestController
@RequestMapping
public class TransferController {

    @Resource
    private TransferService transferService;

    @PostMapping("/queryTransfer")
    public List<TransferRecord> queryTransfer(String inOrOut, String month, String card, String type, Timestamp startTime, Timestamp endTime){
        List<TransferRecord> transferRecords = transferService.queryTransfer(inOrOut, month, card, type, startTime, endTime);
        return transferRecords;
    }

    @RequestMapping("/queryTransferDetail")
    public TransferRecord queryTransferDetail(String id){
        TransferRecord transferRecord = transferService.selectTransferById(id);
        return transferRecord;
    }

    /**
     * 增加一条转账记录
     * @param transferRecord
     * @return Result
     */
    @PostMapping("/addTransferRecord")
    public Result add(@RequestBody TransferRecord transferRecord) {
        return transferService.add(transferRecord);
    }

}
