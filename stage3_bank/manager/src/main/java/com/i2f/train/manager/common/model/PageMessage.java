package com.i2f.train.manager.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-04-09 20:37
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageMessage {
    /**
     * 数据总量
     */
    private int total;
    /**
     * 当前页码
     */
    private int page;
    /**
     * 分页大小
     */
    private int pageSize;
    /**
     * 当页数据
     */
    private List list;
    /**
     * 总页数
     */
    private int pages;
}
