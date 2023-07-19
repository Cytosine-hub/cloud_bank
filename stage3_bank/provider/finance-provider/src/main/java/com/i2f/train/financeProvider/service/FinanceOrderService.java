package com.i2f.train.financeProvider.service;

import com.i2f.train.starter.model.FinanceHold;
import com.i2f.train.starter.model.FinanceOrder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/02/15:29
 * @Description:
 */
public interface FinanceOrderService {
    int addFinanceOrder(FinanceOrder financeOrder);
    int updateFinanceOrder(FinanceOrder financeOrder);
    List<FinanceOrder> selectSubscribeOrderAndNotConfirm();
    FinanceOrder selectFinanceOrderById(String id);
}
