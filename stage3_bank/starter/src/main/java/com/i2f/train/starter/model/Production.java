package com.i2f.train.starter.model;

import lombok.*;

import java.io.Serializable;

/**
 * @author Cytosine
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Production implements Serializable {
    private String id;

    /**
    * 商品名称
    */
    private String name;

    /**
    * 商品描述
    */
    private String description;

    /**
    * 库存数量
    */
    private String stock;

    /**
    * 积分价格
    */
    private String price;

    /**
    * 0-下架
1-上架
    */
    private String state;

    /**
    * 备用字段1
    */
    private String remark1;

    private String remark2;

    /**
    * 商品类型
    */
    private String type;

    public int inState(){
        return Integer.valueOf(state);
    }
}