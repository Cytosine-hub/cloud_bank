package com.i2f.train.manager.common.util;

import com.google.common.collect.Lists;
import com.i2f.train.manager.common.model.PageMessage;

import java.util.List;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-04-09 20:36
 * 对List进行分页
 **/
public class PagedListUtil {
    public static <T> PageMessage pageList(List<T> list,int page,int pageSize){
        PageMessage pageMessage = new PageMessage();
        //设置分页大小和总数
        pageMessage.setPageSize(pageSize);
        pageMessage.setTotal(list.size());

        if(list== null || list.isEmpty()){
            pageMessage.setPages(0);
            pageMessage.setPage(1);
            return pageMessage;
        }

        List<List<T>> partition = Lists.partition(list, pageSize);
        //如果分页总数比传进来的页数小
        if (page>= partition.size()){
            //返回最后一页
            pageMessage.setPage(partition.size());
            pageMessage.setList(partition.get(partition.size() - 1));
        }else {
            pageMessage.setPage(page);
            pageMessage.setList(partition.get(page - 1));
        }
        //设置总页数
        pageMessage.setPages(partition.size());
        return pageMessage;
    }
}
