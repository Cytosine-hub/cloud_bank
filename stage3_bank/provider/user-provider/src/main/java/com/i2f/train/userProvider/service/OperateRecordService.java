package com.i2f.train.userProvider.service;

import com.i2f.train.starter.model.OperateRecord;

/**
 * @author: 刘志亮
 * @date: 2022/3/31 10:31
 */
public interface OperateRecordService {
    /**
     * 增加一条操作记录
     * @param operateRecord
     * @return boolean
     */
    boolean operateRecordAdd(OperateRecord operateRecord);

    /**
     * 修改可疑记录
     * @param id
     * @param success
     * @return
     */

    boolean updateOperateRecord(String id, String success);

    boolean openAccountOperateRecord(OperateRecord operateRecord);

    boolean updateOperate(OperateRecord operateRecord);
}
