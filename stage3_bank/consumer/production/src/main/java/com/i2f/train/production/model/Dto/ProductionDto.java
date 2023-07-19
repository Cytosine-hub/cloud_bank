package com.i2f.train.production.model.Dto;/*
WDNG
 四月01号11:22
*/

import lombok.Data;

@Data
public class ProductionDto {
    /**
     * 剩余库存
     */
    private String stock;
    /**
     * 用户剩余积分
     */
    private int integral;
}
