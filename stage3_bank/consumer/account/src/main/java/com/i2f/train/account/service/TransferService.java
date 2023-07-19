package com.i2f.train.account.service;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.TransferRecord;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/03/30/17:25
 * @Description:
 */
public interface TransferService {
    Result transferDetail(String inOrOut, String month, String card, String type, Timestamp startTime, Timestamp endTime, String userId);
    Result transfer(String id);
}
