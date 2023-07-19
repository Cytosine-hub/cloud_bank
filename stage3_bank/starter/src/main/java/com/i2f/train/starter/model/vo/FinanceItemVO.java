package com.i2f.train.starter.model.vo;

import com.i2f.train.starter.model.FinanceItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/14/13:27
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinanceItemVO {
    private FinanceItem financeItem;
    private List<Double> rateList;
}
