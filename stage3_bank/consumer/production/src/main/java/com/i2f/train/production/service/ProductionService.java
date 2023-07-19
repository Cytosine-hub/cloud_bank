package com.i2f.train.production.service;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.Production;
import com.i2f.train.starter.model.User;

import java.util.Map;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 15:23
 */
public interface ProductionService {
    /**
     * 积分抽奖
     * @return
     */
    Result bonusPointDraw(String integral);

    /**
     * 积分兑换
     * @param map
     * @return
     */
    Result pointConversion(Map<String ,String> map);

    /**
     * 获取积分商品列表
     * @return
     */
    Result getList();


    /**
     * 获取商品详细信息
     * @param id
     * @return
     */
    Result getDetail(String id);
}
