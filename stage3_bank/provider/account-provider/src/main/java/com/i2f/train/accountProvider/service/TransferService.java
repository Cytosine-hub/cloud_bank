package com.i2f.train.accountProvider.service;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.TransferRecord;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/03/30/20:30
 * @Description:
 */
public interface TransferService {
    List<TransferRecord> queryTransfer(String inOrOut, String month, String card, String type, Timestamp startTime, Timestamp endTime);

    TransferRecord selectTransferById(String id);

    /**
     * 增加一条转账记录
     * @param transferRecord
     * @return Result
     */
    Result add(TransferRecord transferRecord);

    /**
     * 查询当日的支出记录
     * @param outAccountId
     * @return
     */
    List<TransferRecord> getDailyRecord(String outAccountId);

    /**
     * 查询当日的支出总金额
     * @param outAccountId
     * @return
     */
    BigDecimal getAllDailyRecord(String outAccountId);
}
