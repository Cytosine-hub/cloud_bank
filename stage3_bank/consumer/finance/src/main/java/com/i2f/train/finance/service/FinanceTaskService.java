package com.i2f.train.finance.service;

import com.i2f.train.starter.common.model.Result;

import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/06/9:28
 * @Description:
 */
public interface FinanceTaskService {

    void installRate();

    void updateMoney();

    void updateStatus();

    void exportSubscribeFile();

    /**
     * 将需要赎回的申请导出成excel
     */
    void saleBackPartsExcel();

    /**
     * 赎回
     */
    void saleBackParts();
    /**
     * 基金公司赎回回盘
     */
    void  excelsaleback();

}
