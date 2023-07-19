package com.i2f.train.core.Service;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.FinanceHold;
import com.i2f.train.starter.model.FinanceOrder;
import org.springframework.stereotype.Service;
import com.i2f.train.starter.model.vo.UpdateMoneyVO;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/02/10:00
 * @Description:
 */
public interface FinanceCoreService {
    int addFinanceOrder(FinanceOrder financeOrder);

    Result subscribe(UpdateMoneyVO updateMoneyVO);

    int updateFinanceOrder(FinanceOrder financeOrder);

    int addFinanceHold(FinanceHold financeHold);

    int updateFinanceHold(FinanceHold financeHold);
}
