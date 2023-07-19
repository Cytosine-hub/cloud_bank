package com.i2f.train.starter.model.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: hjx
 * @Date: 2022/04/09/9:35
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyFinanceOrderVO implements Serializable {
    @Excel(name = "流水号",width = 20)
    private String id;
    @Excel(name = "用户姓名",width = 20)
    private String username;
    @Excel(name = "产品Id",width = 20)
    private String financeId;
    @Excel(name = "产品名称",width = 20)
    private String financeName;
    @Excel(name = "购买时间",width = 20, format = "yyyy-MM-dd HH:mm:ss")
    private Date subscribeTime;
    @Excel(name = "购买金额",width = 20)
    private String money;
    @Excel(name = "购买份额",width = 20)
    private String amount;
    @Excel(name = "申购状态",width = 20)
    private String status;
}
