package com.i2f.train.starter.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 13:44
 */
public class UserAccountRelation implements Serializable {
    /**
    * 账号
    */
    private String accountId;

    /**
    * 用户ID
    */
    private String userId;

    /**
    * 绑卡时间
    */
    private Date createTime;

    /**
    * 是否是主卡
    */
    private String isMain;

    /**
    * 备用字段1
    */
    private String remark1;

    /**
    * 备用字段2
    */
    private String remark2;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIsMain() {
        return isMain;
    }

    public void setIsMain(String isMain) {
        this.isMain = isMain;
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