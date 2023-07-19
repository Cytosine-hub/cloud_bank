package com.i2f.train.manager.common.constants;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-03-31 10:13
 **/
public class ManagerConstants {
    public interface Code{
        String SUCCESS = "S000000";
        String ADD_FAILED = "M111111";
        String LOGIN_FAILED = "M000001";
        String LEVEL_ERROR = "M000002";
        String DELETE_FAILED = "E00003";
        String QUERY_FAILED = "E00004";
        String MODIFY_FAILED = "E00005";
        String DELETE_PHONE_FAILED="E00006";
        String DELETE_LEVEL_FAILED="E00007";
        String ADD_DEFICIENCY_FAILED = "E00008";
        String FILE_NOT_FOUND = "E00009";
        String FILE_FAILED = "E00010";
        String NOT_LOGIN = "E00011";

    }

    public interface Message{
        String SUCCESS_MESSAGE = "操作成功";
        String ADD_FAILED_MESSAGE = "新增失败";
        String DELETE_FAILED_MESSAGE = "删除失败";
        String QUERY_FAILED_MESSAGE = "管理员列表查询失败";
        String MODIFY_FAILED_MESSAGE = "个人信息修改失败";
        String DELETE_PHONE_FAILED_MESSAGE="删除失败，不能删除自己";
        String DELETE_LEVEL_FAILED_MESSAGE="删除失败,您没有权限删除比你权限高或一样的管理员";
        String ADD_DEFICIENCY_FAILED_MESSAGE = "管理员信息不足";
        String FILE_NOT_FOUND_MESSAGE = "文件不存在";
        String FILE_FAILED_MESSAGE = "下载失败";
        String NOT_LOGIN_MESSAGE = "请先登录！";
    }
}
