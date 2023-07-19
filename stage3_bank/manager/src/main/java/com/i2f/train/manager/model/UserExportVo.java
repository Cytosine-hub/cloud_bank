package com.i2f.train.manager.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: stage3
 * @author: Cytosine
 * @create: 2022-03-19 14:31
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserExportVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "姓名",orderNum = "0",width = 15)
    private String name;

    @Excel(name = "电话",orderNum = "1",width = 15)
    private String phoneNum;

    @Excel(name = "年龄",orderNum = "2",width = 15)
    private int age;

    @Excel(name = "住址",orderNum = "3",width = 15)
    private String address;

}
