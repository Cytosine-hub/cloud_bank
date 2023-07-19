package com.i2f.train.starter.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *基金产品
 * @author wqshandwzh
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinanceItemMoveVo implements Serializable {
     private String fiId;
     private List<Double> financeRate;
     private String fiName;
     private String fiMin;
     private String fiMax;
     private Double yesterdayRate;
}
