package com.i2f.train.finance.controller;

import com.i2f.train.finance.service.FinanceTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/07/10:22
 * @Description:
 */
@RestController
@RequestMapping("/financeTask")
public class FinanceTaskController {
    @Autowired
    private FinanceTaskService financeTaskService;

    @RequestMapping("/test1")
    public void installRate(){
        financeTaskService.installRate();
    }

    @RequestMapping("/test2")
    public void updateMoney(){
        financeTaskService.updateMoney();
    }

    @RequestMapping("/test3")
    public void updateStatus(){
        financeTaskService.updateStatus();
    }

    @RequestMapping("/exportSubscribeFile")
    public void exportExcel(){
        financeTaskService.exportSubscribeFile();
    }
}
