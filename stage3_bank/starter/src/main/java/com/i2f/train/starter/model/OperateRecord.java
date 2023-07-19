package com.i2f.train.starter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 13:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperateRecord implements Serializable {
    /**
    * 流水ID
    */
    private String id;

    /**
    * 操作用户ID
    */
    private String userId;

    /**
    * 操作类型
    */
    private String type;

    /**
    * 状态
    */
    private String status;

    /**
    * 操作时间
    */
    private Date time;

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

    /**
    * 转入账户ID
    */
    private String inAccountId;

    /**
    * 转出账户ID
    */
    private String outAccountId;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
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
}