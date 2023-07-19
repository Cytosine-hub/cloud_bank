package com.i2f.train.starter.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 13:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRecord implements Comparable<TransferRecord>, Serializable {
    /**
    * 转账记录ID
    */
    private String id;

    /**
    * 操作流水对应ID
    */
    private String uuid;

    /**
    * 转入账户ID
    */
    private String inAccountId;

    /**
    * 支出账户ID
    */
    private String outAccountId;

    /**
    * 类型
    */
    private String type;

    /**
    * 转账金额
    */
    private BigDecimal money;

    /**
    * 转账时间
    */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
    * 转账留言
    */
    private String remark;

    /**
    * 手续费
    */
    private BigDecimal otherMoney;

    /**
    * 备用字段1
    */
    private String remark1;

    /**
    * 备用字段2
    */
    private String remark2;

    /**
    * 备用字段3
    */
    private String remark3;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getInAccountId() {
        return inAccountId;
    }

    public void setInAccountId(String inAccountId) {
        this.inAccountId = inAccountId;
    }

    public String getOutAccountId() {
        return outAccountId;
    }

    public void setOutAccountId(String outAccountId) {
        this.outAccountId = outAccountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getOtherMoney() {
        return otherMoney;
    }

    public void setOtherMoney(BigDecimal otherMoney) {
        this.otherMoney = otherMoney;
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

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    @Override
    public int compareTo(TransferRecord o) {
        return o.getCreateTime().compareTo(this.getCreateTime());
    }
}