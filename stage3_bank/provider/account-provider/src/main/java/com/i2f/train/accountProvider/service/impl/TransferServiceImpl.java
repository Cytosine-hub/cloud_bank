package com.i2f.train.accountProvider.service.impl;

import com.i2f.train.accountProvider.mapper.TransferRecordMapper;
import com.i2f.train.accountProvider.service.TransferService;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.common.util.UUIDGenerator;
import com.i2f.train.starter.model.TransferRecord;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/03/30/20:30
 * @Description:
 */
@Service
public class TransferServiceImpl implements TransferService {
    @Resource
    private UUIDGenerator uuidGenerator;

    @Resource
    private TransferRecordMapper transferRecordMapper;

    @Override
    public List<TransferRecord> queryTransfer(String inOrOut, String month, String card, String type, Timestamp startTime, Timestamp endTime) {
        List<TransferRecord> transferRecords = transferRecordMapper.selectByConditions(inOrOut, month, card, type, startTime, endTime);
        return transferRecords;
    }

    @Override
    public TransferRecord selectTransferById(String id) {
        TransferRecord transferRecord = transferRecordMapper.selectByPrimaryKey(id);
        return transferRecord;
    }

    /**
     * 增加一条转账记录
     * @param transferRecord
     * @return Result
     */
    @Override
    public Result add(TransferRecord transferRecord) {
        transferRecord.setId(uuidGenerator.getId());
        transferRecordMapper.insertSelective(transferRecord);
        return Result.success("");
    }

    /**
     * 查询当日的支出记录
     * @param outAccountId
     * @return
     */
    @Override
    public List<TransferRecord> getDailyRecord(String outAccountId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String now = sdf.format(new Date());
        return transferRecordMapper.getDailyRecord(now, outAccountId);
    }

    /**
     * 查询当日的支出总金额
     * @param outAccountId
     * @return
     */
    @Override
    public BigDecimal getAllDailyRecord(String outAccountId) {
        List<TransferRecord> transferRecords = getDailyRecord(outAccountId);
        //判断当日是否有支出金额
        if (transferRecords == null || transferRecords.isEmpty()){
            return new BigDecimal("0.00");
        }
        //记录当日支出总金额
        BigDecimal total = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        for (int i = 0; i < transferRecords.size(); i++) {
            total = total.add(transferRecords.get(i).getMoney());
        }
        return total;
    }
}
