package com.i2f.train.starter.model.queryMap;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-03-31 15:28
 **/
@Data
public class FinanceQueryMap implements Serializable {
    /**
     * 理财产品id
     */
    private String id;

    /**
     * 理财产品名称
     */
    private String name;

    /**
     * 理财产品风险等级(1-低风险、2-中风险、3-高风险)
     */
    private String level;

    /**
     * 理财产品类型
     */
    private String type;

    /**
     * 理财产品年化利率
     */
    private String rate;

    /**
     * 理财产品周期（天）
     */
    private String time;

    /**
     * 理财产品最小购买金额
     */
    private String min;

    /**
     * 理财产品最大购买金额
     */
    private String max;

    /**
     * 理财产品预计申购确认时间（天）
     */
    private String confirmTime;

    /**
     * 理财产品状态（0-下架、1-上架）
     */
    private String isOnsale;

    /**
     * 理财产品总份额（元）
     */
    private String share;

    /**
     * 理财产品是否被删除
     */
    private String isdeleted;

    /**
     * 备用字段1
     */
    private Integer remark1;

    /**
     * 备用字段2
     */
    private Integer remark2;
    private int page;
    private int pageSize;
}
