package com.i2f.train.production.service.impl;/*
WDNG
 三月31号16:50
*/

import com.i2f.train.production.feign.ProductionFeign;
import com.i2f.train.production.feign.UserFeign;
import com.i2f.train.production.model.Dto.ProductionDto;
import com.i2f.train.production.service.ProductionService;
import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.common.util.JwtUtils;
import com.i2f.train.starter.model.Production;
import com.i2f.train.starter.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class ProductionServiceImpl implements ProductionService {
    @Resource
    UserFeign userFeign;
    @Resource
    ProductionFeign productionFeign;

    /**
     * 积分抽奖
     *
     * @return
     */
    @Override
    public Result bonusPointDraw(String integral) {

        //从token中获得userid
         String userId = JwtUtils.getUserId();

        //根据userid得到用户信息
        User user = userFeign.selectByPrimaryKey(userId);
        if (user.getIntegral() < 10) {
            return Result.error(CommonConstants.Request.BONUSPOINTDRAW_ERROR, CommonConstants.Request.BONUSPOINTDRAW_ERROR_MESSAGE);
        }
        int i = addIntegral(Integer.parseInt(integral), userId);

        if (i == 0) {
            return Result.error(CommonConstants.Request.INTERNAL_ERROR, CommonConstants.Request.INTERNAL_ERROR_MESSAGE);
        }
        User users = userFeign.selectByPrimaryKey(userId);
        return Result.success(users.getIntegral(), CommonConstants.Request.SUCCESS, "抽奖成功");
    }


    /**
     * 积分抽奖增加积分
     *
     * @param integral
     * @param userId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int addIntegral(int integral, String userId) {
        User user = userFeign.selectByPrimaryKey(userId);
        user.setIntegral(user.getIntegral() - 10 + integral);
        userFeign.updateByPrimaryKeySelective(user);
        return 1;
    }

    /**
     * 积分兑换
     *
     * @param map
     * @return
     */
    @Override
    public Result pointConversion(Map<String, String> map) {
        String productionId = map.get("productionId");
        String amount = map.get("amount");
        //从token中获得userid
         String userId = JwtUtils.getUserId();
        Production production = productionFeign.selectByPrimaryKey(productionId);
        User user = userFeign.selectByPrimaryKey(userId);
        if (Integer.parseInt(production.getStock()) < Integer.parseInt(amount)) {
            return Result.error(CommonConstants.Request.PRODUCTION_STOCK_ERROR, CommonConstants.Request.PRODUCTION_STOCK_ERROR_MESSAGE);
        }
        if (Integer.parseInt(production.getPrice()) * Integer.parseInt(amount) > user.getIntegral()) {
            return Result.error(CommonConstants.Request.PRODUCTION_PRICE_ERROR, CommonConstants.Request.PRODUCTION_PRICE_ERROR_MESSAGE);
        }
        int i = updateProduction(productionId, amount, userId);
        if (i == 0) {
            return Result.error(CommonConstants.Request.INTERNAL_ERROR, CommonConstants.Request.INTERNAL_ERROR_MESSAGE);
        }
        User userLast = userFeign.selectByPrimaryKey(userId);
        Production productionLast = productionFeign.selectByPrimaryKey(productionId);
        ProductionDto productionDto = new ProductionDto();
        productionDto.setStock(productionLast.getStock());
        productionDto.setIntegral(userLast.getIntegral());
        return Result.success(productionDto, CommonConstants.Request.SUCCESS, "兑换成功");
    }

    @Override
    public Result getList() {
        Production production = new Production();
        production.setState("1");
        return productionFeign.getProductions(production);
    }

    @Override
    public Result getDetail(String id) {
        Production production = productionFeign.selectByPrimaryKey(id);
        if (production == null){
            return Result.error(CommonConstants.Request.PRODUCTION_DETAIL_ERROR, "查询失败");
        }
        return Result.success(production);
    }

    /**
     * 减少积分，减少库存
     *
     * @param productionId
     * @param amount
     * @param userId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateProduction(String productionId, String amount, String userId) {
        User user = userFeign.selectByPrimaryKey(userId);
        Production production = productionFeign.selectByPrimaryKey(productionId);
        //算出总积分
        int i = Integer.parseInt(production.getPrice()) * Integer.parseInt(amount);
        user.setIntegral(user.getIntegral() - i);
        userFeign.updateByPrimaryKeySelective(user);
        production.setStock(String.valueOf(Integer.parseInt(production.getStock()) - Integer.parseInt(amount)));
        productionFeign.updateByPrimaryKeySelective(production);
        return 1;
    }
}
