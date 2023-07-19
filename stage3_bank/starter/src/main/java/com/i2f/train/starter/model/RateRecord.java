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

/**
    * 利率表
    */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RateRecord implements Serializable {
    private String id;

    public RateRecord(String id, String financeId, Double rate, Date time) {
        this.id = id;
        this.financeId = financeId;
        this.rate = rate;
        this.time = time;
    }

    /**
    * 产品id
    */
    private String financeId;

    /**
    * 利率
    */
    private Double rate;

    /**
    * 时间
    */
    private Date time;

    private String remark1;

    private String remark2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFinanceId() {
        return financeId;
    }

    public void setFinanceId(String financeId) {
        this.financeId = financeId;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }
}