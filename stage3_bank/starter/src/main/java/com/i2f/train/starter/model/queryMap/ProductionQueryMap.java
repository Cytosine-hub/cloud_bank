package com.i2f.train.starter.model.queryMap;

import com.i2f.train.starter.model.Production;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-04-09 10:04
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionQueryMap implements Serializable {
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
     * 商品类型
     */
    private String type;

    private int page;

    private int pageSize;

    public Production toProduction(){
        return new Production(id, name, description, stock, price, state, null, null, type);
    }
}
