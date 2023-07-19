package com.i2f.train.account.controller;

import com.i2f.train.account.service.TransferService;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.common.util.JwtUtils;
import com.i2f.train.starter.model.vo.TransferVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/03/30/17:26
 * @Description:
 */
@RestController
@RequestMapping("/account")
public class TransferController {

    @Resource
    private TransferService transferService;

    @RequestMapping("/transferDetails")
    public Result transferDetail(@RequestBody TransferVO transferVO){
        String userId = JwtUtils.getUserId();
        String type = transferVO.getType();
        String card = transferVO.getCard();
        String month = transferVO.getMonth();
        String inOrOut = transferVO.getInOrOut();
        Timestamp startTime = transferVO.getStartTime();
        Timestamp endTime = transferVO.getEndTime();
        Result result = transferService.transferDetail(inOrOut, month, card, type, startTime, endTime, userId);
        return result;
    }

    @PostMapping("/transferDetail")
    public Result transfer(String id){
        Result transfer = transferService.transfer(id);
        return transfer;
    }
}
