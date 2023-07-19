package com.i2f.train.financeProvider.service.impl;

import com.i2f.train.financeProvider.mapper.FinanceOrderMapper;
import com.i2f.train.financeProvider.service.FinanceOrderService;
import com.i2f.train.starter.model.FinanceOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/02/15:29
 * @Description:
 */
@Service
public class FinanceOrderServiceImpl implements FinanceOrderService {

    @Autowired
    FinanceOrderMapper financeOrderMapper;
    @Override
    public int addFinanceOrder(FinanceOrder financeOrder) {
        return financeOrderMapper.insertSelective(financeOrder);
    }

    @Override
    public int updateFinanceOrder(FinanceOrder financeOrder) {
        return financeOrderMapper.updateByPrimaryKeySelective(financeOrder);
    }

    @Override
    public List<FinanceOrder> selectSubscribeOrderAndNotConfirm() {
        return financeOrderMapper.selectSubscribeOrderAndNotConfirm();
    }

    @Override
    public FinanceOrder selectFinanceOrderById(String id) {
        return financeOrderMapper.selectByPrimaryKey(id);
    }


}
