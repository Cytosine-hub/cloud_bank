package com.i2f.train.starter.model.vo;

import com.i2f.train.starter.model.FinanceHold;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Wqsh
 * @version 1.0
 * @date 2022/4/2 14:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinanceHoldVO implements Serializable {
    String FinanceItemName;
    String level;
    FinanceHold financeHold;
}
