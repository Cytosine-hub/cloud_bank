package com.i2f.train.starter.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: 刘志亮
 * @date: 2022/4/1 10:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BindCardVO implements Serializable {
    /**
     * 绑卡时为银行预留手机号
     * 其它为接收验证码的手机号
     */
    private String phone;
    /**
     * 银行卡卡号
     */
    private String accountId;
    /**
     * 用户输入的验证码
     */
    private String userCode;
}
