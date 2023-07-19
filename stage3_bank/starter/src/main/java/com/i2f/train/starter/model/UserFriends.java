package com.i2f.train.starter.model;

import java.io.Serializable;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 13:44
 */
public class UserFriends implements Serializable {
    private String id;

    /**
    * 用户ID
    */
    private String userId;

    /**
    * 收款人姓名
    */
    private String friendName;

    /**
    * 收款账户
    */
    private String accountId;

    /**
    * 银行名称
    */
    private String bankName;

    /**
    * 备用字段2
    */
    private String remark2;

    /**
    * 备用字段1
    */
    private String remark1;

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

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }
}