package com.i2f.train.starter.common.constants;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-03-31 10:13
 **/
public class FinanceItemConstants {
    public interface Code{
        String SUCCESS = "S000000";
        String ADD_FAILED = "M111111";
        String DELETED_FAILED = "M222222";
        String SELECT_FAILED = "M333333";
        String UPDATE_FAILED = "M444444";
        String LEVEL_FAILED = "M555555";
    }

    public interface Message{
        String SUCCESS_MESSAGE = "操作成功";
        String ADD_FAILED_MESSAGE = "新增失败";
        String DELETED_FAILED_MESSAGE="删除产品失败";
        String SELECT_FAILED_MESSAGE="查询产品失败";
        String UPDATE_FAILED_MESSAGE ="修改产品失败";
    }
}
