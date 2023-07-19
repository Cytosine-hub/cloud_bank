package com.i2f.train.starter.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: 刘志亮
 * @date: 2022/4/1 10:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckAccountVO implements Serializable {
    /**
     * 身份证号
     */
    private String personalId;
    /**
     * 银行卡卡号
     */
    private String id;
    /**
     * 银行卡预留手机号
     */
    private String phone;
}
