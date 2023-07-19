package com.i2f.train.manager.service;

import com.i2f.train.manager.model.Manager;
import com.i2f.train.manager.model.queryMap.ManagerQueryMap;
import com.i2f.train.manager.model.vo.ManagerVo;
import com.i2f.train.starter.common.model.Result;

import java.util.Map;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 11:17
 */
public interface ManagerService {

    /**
     * 新增管理员
     *
     * @param manager
     * @return
     */
    Result addNewManager(Manager manager);

    /**
     * 登录
     *
     * @param managerVo
     * @return
     */
    Result login(ManagerVo managerVo);

    /**
     * 修改管理员权限等级
     *
     * @param map 包括管理员id 和 修改后 的等级
     * @return
     */
    Result levelChange(Map<String, String> map);

    /**
     * 删除管理员
     *
     * @param id
     * @return
     */
    Result delete(String id);

    /**
     * 查询管理员列表
     * @param queryMap
     * @return
     */
    Result queryManagers(ManagerQueryMap queryMap);

    /**
     * 修改个人信息
     * @param manager
     * @return
     */
    Result modify(Manager manager);

    /**
     * 获取首页统计数据
     * @return
     */
    Result getTotal();
}
