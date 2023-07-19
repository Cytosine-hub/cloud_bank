package com.i2f.train.finance.common.util;

import com.i2f.train.finance.service.FinanceTaskService;
import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.FinanceOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/06/14:24
 * @Description:
 */
@Component
@EnableScheduling
@Slf4j
public class FinanceTask {
    @Autowired(required = false)
    private FinanceTaskService financeTaskService;

    @Scheduled(cron = "0/10 * * * * ? ")
    public void test(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = attributes.getRequest();
        request.getSession();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    private void doTask() {
        try {
            financeTaskService.installRate();
            financeTaskService.updateMoney();
        } catch (Exception e) {
            log.info("当前定时器有误！");
        }

    }
    /**
     * 赎回
     */
    @Scheduled(cron = "0 */6 * * * ?")
    public  void  sale(){
        try {
            financeTaskService.saleBackParts();

        } catch (Exception e) {
            log.info("当前定时器有误！");
        }

    }
    /**
     * 生成excel
     */
    @Scheduled(cron = "0 */2 * * * ?")
    public  void  excelsale(){
        try {
            financeTaskService.saleBackPartsExcel();
        } catch (Exception e) {
            log.info("当前定时器有误！");
        }

    }
    /**
     * 基金公司赎回回盘
     */
    @Scheduled(cron = "0 */4 * * * ?")
    public  void  excelsaleback(){
        try {
        financeTaskService.excelsaleback();
        } catch (Exception e) {
            log.info("当前定时器有误！");
        }

    }


    @Scheduled(cron = "0 0 9-15 * * ?")
    public void subscribe(){
        try {
            financeTaskService.exportSubscribeFile();
            financeTaskService.updateStatus();
        } catch (Exception e) {
            log.info("当前定时器有误！");
        }

    }
}
