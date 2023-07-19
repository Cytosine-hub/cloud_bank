package com.i2f.train.starter.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 13:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceOrder implements Serializable {
    /**
     * 理财订单id
     */
    @Excel(name="理财订单id",width = 20)
    private String id;

    /**
     * 理财产品id
     */
    @Excel(name="理财产品id",width = 20)
    private String financeId;

    /**
     * 用户id
     */
    @Excel(name="用户id",width = 20)
    private String userId;

    /**
     * 订单创建时间
     */
    @Excel(name="订单创建时间",width = 20,format = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;

    /**
     * 订单确认时间
     */
    @Excel(name="订单确认时间",width = 20,format = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date confirmTime;

    /**
     * 订单金额
     */
    @Excel(name="订单金额",width = 20)
    private String money;

    /**
     * 订单类型（1-申购、2-赎回）
     */
    @Excel(name="订单类型（",width = 20)
    private String type;

    /**
     * 订单最终状态(0-失败 , 1-成功,2-处理中)
     */
    @Excel(name="订单最终状态",width = 20)
    private String status;
    /**
     * 购买份额
     */
    @Excel(name="购买份额",width = 20)
    private String amount;
    
}