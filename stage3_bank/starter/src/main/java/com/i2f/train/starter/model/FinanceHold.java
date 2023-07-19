package com.i2f.train.starter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 13:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceHold implements Serializable {
    /**
    * 理财产品持有表id
    */
    private String id;

    /**
    * 持有人id
    */
    private String userId;

    /**
    * 持有理财产品id
    */
    private String financeId;

    /**
    * 持有金额
    */
    private String money;

    /**
    * 开始持有时间
    */
    private Date time;

    /**
    * 持有昨日收益
    */
    private String profit;

    /**
    * 总收益
    */
    private String totalProfit;
    /**
     * 持有份额
     */
    private String amount;

    /**
     *
     * 是否持有
     */
    private  String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFinanceId() {
        return financeId;
    }

    public void setFinanceId(String financeId) {
        this.financeId = financeId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(String totalProfit) {
        this.totalProfit = totalProfit;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }




}