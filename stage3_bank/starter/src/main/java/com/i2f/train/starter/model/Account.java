package com.i2f.train.starter.model;

import com.i2f.train.starter.annoation.CryptData;
import com.i2f.train.starter.annoation.CryptField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 13:40
 */

/**
    * 账户ID
    */

@Data
@AllArgsConstructor
@NoArgsConstructor
@CryptData
public class Account implements Serializable {
    /**
    * 账户ID（银行卡号）
    */
    private String id;

    /**
    * 账户类型 1-一类 2-二类
    */
    private String type;

    /**
    * 账户支付密码
    */
    @CryptField
    private String payPassword;

    /**
    * 开户行名字
    */
    private String bankName;

    /**
    * 用户姓名
    */
    private String username;

    /**
    * 身份证号
    */
    private String personalId;

    /**
    * 余额
    */
    @CryptField
    private String money;

    /**
    * 每日支付限额
    */
    private BigDecimal dailyMoney;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 用户手机号
    */
    private String mobilePhone;

    /**
    * 0-未冻结 1-冻结
    */
    private String state;

    private String remark1;

    private String remark2;

    private String remark3;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public BigDecimal getDailyMoney() {
        return dailyMoney;
    }

    public void setDailyMoney(BigDecimal dailyMoney) {
        this.dailyMoney = dailyMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
}