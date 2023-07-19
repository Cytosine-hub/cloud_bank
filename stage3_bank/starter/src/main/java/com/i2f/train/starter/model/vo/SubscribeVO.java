package com.i2f.train.starter.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/01/14:25
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeVO implements Serializable {
    private String financeId;
    private BigDecimal money;
    private String payPassword;
}
