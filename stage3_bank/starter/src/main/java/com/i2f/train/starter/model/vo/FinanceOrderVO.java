package com.i2f.train.starter.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wqsh
 * @version 1.0
 * @date 2022/4/4 11:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinanceOrderVO implements Serializable {
    //订单id
    private String id;
    //基金名称
    private String FiName;
    //订单金额
    private String foMoney;
    //订单创建时间
    private Date foCreateTime;
    //订单确认时间
    private Date foConfirmTime;
    //订单类型，1-申购，2-赎回
    private String foType;
    //2-未确认，0-未成功，1-成功
    private String status;
    //份额
    private String account;

}
