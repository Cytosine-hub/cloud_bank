package com.i2f.train.starter.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Wqsh
 * @version 1.0
 * @date 2022/4/13 11:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoldRecordVO {
    BigDecimal steadyLevelMoney;
    BigDecimal aggressiveLevelMoney;
    BigDecimal totalMoney;
    BigDecimal profitMoney;
    BigDecimal totalProfitMoney;
}
