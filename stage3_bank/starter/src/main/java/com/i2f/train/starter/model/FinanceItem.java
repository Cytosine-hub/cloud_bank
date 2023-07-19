package com.i2f.train.starter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 13:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceItem implements Serializable {
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

    public int intType(){
        return Integer.valueOf(type);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getIsOnsale() {
        return isOnsale;
    }

    public void setIsOnsale(String isOnsale) {
        this.isOnsale = isOnsale;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(String isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Integer getRemark1() {
        return remark1;
    }

    public void setRemark1(Integer remark1) {
        this.remark1 = remark1;
    }

    public Integer getRemark2() {
        return remark2;
    }

    public void setRemark2(Integer remark2) {
        this.remark2 = remark2;
    }
}