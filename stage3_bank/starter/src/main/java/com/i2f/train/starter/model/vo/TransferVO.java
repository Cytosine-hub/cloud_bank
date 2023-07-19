package com.i2f.train.starter.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/03/30/17:31
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferVO implements Serializable {
    private String inOrOut;
    private String month;
    private String card;
    private String type;
    private Timestamp startTime;
    private Timestamp endTime;
}
