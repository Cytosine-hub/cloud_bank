package com.i2f.train.starter.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: 刘志亮
 * @date: 2022/4/1 10:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMoneyVO implements Serializable {
    /**
     * 支付密码
     */
    private String payPassword;
    /**
     * 付款银行卡号
     */
    private String payAccount;
    /**
     * 付款金额
     */
    private String payMoney;
    /**
     * 收款银行卡号
     */
    private String receiverAccount;
    /**
     * 收款人姓名
     */
    private String receiverName;
    /**
     * 交易类型
     * 充值--1
     * 提现--2
     * 转账--3
     */
    private String type;
    /**
     * 手续费
     */
    private String otherMoney;
    /**
     * 操作流水对应ID
     */
    private String uuid;
    /**
     * 转账留言
     */
    private String message;

    public UpdateMoneyVO(String payPassword, String payAccount, String payMoney, String receiverAccount, String receiverName, String type) {
        this.payPassword = payPassword;
        this.payAccount = payAccount;
        this.payMoney = payMoney;
        this.receiverAccount = receiverAccount;
        this.receiverName = receiverName;
        this.type = type;
    }
}
