package com.i2f.train.core.Service.impl;

import com.i2f.train.core.Service.FinanceCoreService;
import com.i2f.train.core.feign.AccountCustomerOpenFeign;
import com.i2f.train.core.feign.FinanceOrderOpenFeign;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.common.util.UUIDGenerator;
import com.i2f.train.starter.model.FinanceHold;
import com.i2f.train.starter.model.FinanceOrder;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/02/10:00
 * @Description:
 */
@Service
public class FinanceCoreServiceImpl implements FinanceCoreService {

    @Autowired
    private AccountCustomerOpenFeign accountCustomerOpenFeign;
    @Autowired
    private FinanceOrderOpenFeign financeOrderOpenFeign;
    @Autowired
    private UUIDGenerator uuidGenerator;


    @Override
    public int addFinanceOrder(FinanceOrder financeOrder) {
        return financeOrderOpenFeign.addFinanceOrder(financeOrder);
    }

    @Override
    public Result subscribe(UpdateMoneyVO updateMoneyVO) {
        return accountCustomerOpenFeign.transfer(updateMoneyVO);
    }

    @Override
    public int updateFinanceOrder(FinanceOrder financeOrder) {
        return financeOrderOpenFeign.updateFinanceOrder(financeOrder);
    }

    @Override
    public int addFinanceHold(FinanceHold financeHold) {
        return financeOrderOpenFeign.addFinanceHold(financeHold);
    }

    @Override
    public int updateFinanceHold(FinanceHold financeHold) {
        return financeOrderOpenFeign.updateFinanceHold(financeHold);
    }


}
