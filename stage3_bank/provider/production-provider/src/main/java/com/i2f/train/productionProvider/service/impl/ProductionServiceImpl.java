package com.i2f.train.productionProvider.service.impl;/*
WDNG
 三月31号17:16
*/

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.i2f.train.productionProvider.mapper.ProductionMapper;
import com.i2f.train.productionProvider.service.ProductionService;
import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.common.util.UUIDGenerator;
import com.i2f.train.starter.model.Production;
import com.i2f.train.starter.model.queryMap.ProductionQueryMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductionServiceImpl implements ProductionService {
    @Resource
    ProductionMapper productionMapper;

    @Resource
    UUIDGenerator uuidGenerator;

    /**
     * 根据产品id查找产品信息
     *
     * @param productionId
     * @return
     */
    @Override
    public Production selectProduction(String productionId) {

        return productionMapper.selectByPrimaryKey(productionId);
    }

    /**
     * 修改积分商品
     *
     * @param production
     * @return int
     */
    @Override
    public Result updateByPrimaryKeySelective(Production production) {
        int ok;
        ok = productionMapper.updateByPrimaryKeySelective(production);
        if (ok > 0) {
            return Result.success("");
        }
        return Result.error(CommonConstants.Message.CHANGE_FAILED_CODE, CommonConstants.Message.CHANGE_FAILED_MESSAGE);
    }

    /**
     * 增加积分商品
     *
     * @param production
     * @return Result
     */
    @Override
    public Result add(Production production) {
        //判断是否要求上架，默认下架
        if (StringUtils.isBlank(production.getState())){
            production.setState(CommonConstants.Production.UP_PRODUCTION_STATE_CODE);
        }
        //生成积分商品id
        String id = uuidGenerator.getId();
        production.setId(id);
        //写入数据库
        int ok;
        ok = productionMapper.insertSelective(production);
        if (ok > 0) {
            return Result.success("");
        }
        return Result.error(CommonConstants.Message.ADD_FAILED_CODE, CommonConstants.Message.ADD_FAILED_MESSAGE);
    }

    /**
     * 精确查询积分商品支持全查询
     *
     * @param production
     * @return Result
     */
    @Override
    public Result<List<Production>> selectByAnyField(Production production) {
        ArrayList<Production> productions;
        productions = productionMapper.selectByAnyField(production);
        if (productions.isEmpty()){
            return Result.error(CommonConstants.Existence.NONEXISTENCE_CODE, CommonConstants.Existence.NONEXISTENCE_MESSAGE);
        }
        return Result.success(productions);
    }

    /**
     * 模糊查询积分商品不支持全查询
     *
     * @param queryMap
     * @return Result
     */
    @Override
    public Result selectByAnyFieldLike(ProductionQueryMap queryMap) {
        ArrayList<Production> productions;
        PageHelper.startPage(queryMap.getPage(), queryMap.getPageSize());
        Production production = queryMap.toProduction();
        productions = productionMapper.selectByAnyFieldLike(production);
        if (productions.isEmpty()){
            return Result.error(CommonConstants.Existence.NONEXISTENCE_CODE, CommonConstants.Existence.NONEXISTENCE_MESSAGE);
        }
        PageInfo<Production> productionPageInfo = new PageInfo<>(productions);
        return Result.success(productionPageInfo);
    }

    /**
     * 删除积分商品
     *
     * @param production
     * @return Result
     */
    @Override
    public Result deleteById(Production production) {
        int ok = productionMapper.deleteByPrimaryKey(production.getId());
        if (ok == 0){
            return Result.error(CommonConstants.Existence.NONEXISTENCE_CODE, CommonConstants.Existence.NONEXISTENCE_MESSAGE);
        }
        return Result.success("");
    }
}
