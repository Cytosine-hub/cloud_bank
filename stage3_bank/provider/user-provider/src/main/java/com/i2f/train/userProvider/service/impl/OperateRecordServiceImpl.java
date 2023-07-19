package com.i2f.train.userProvider.service.impl;

import com.i2f.train.starter.common.util.UUIDGenerator;
import com.i2f.train.starter.model.OperateRecord;
import com.i2f.train.userProvider.mapper.OperateRecordMapper;
import com.i2f.train.userProvider.service.OperateRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author: 刘志亮
 * @date: 2022/3/31 10:31
 */
@Service
public class OperateRecordServiceImpl implements OperateRecordService {
    @Resource
    OperateRecordMapper operateRecordMapper;

    @Resource
    UUIDGenerator uuidGenerator;

    /**
     * 增加一条操作记录
     *
     * @param operateRecord
     * @return boolean
     */
    @Override
    public boolean operateRecordAdd(OperateRecord operateRecord) {
        if (StringUtils.isBlank(operateRecord.getId())) {
            operateRecord.setId(uuidGenerator.getId());
        }
        return operateRecordMapper.insertSelective(operateRecord);
    }

    /**
     * 修改可疑记录
     *
     * @param id
     * @param success
     * @return
     */

    @Override
    public boolean updateOperateRecord(String id, String success) {
        operateRecordMapper.updateById(id, success);
        return false;
    }

    /**
     * 开户前添加可疑记录
     *
     * @param operateRecord
     * @return
     */

    @Override
    public boolean openAccountOperateRecord(OperateRecord operateRecord) {
        String id = operateRecord.getId();
        String type = operateRecord.getType();
        String userId = operateRecord.getUserId();
        String status = operateRecord.getStatus();
        Date time = new Date();
        return operateRecordMapper.addOpenAccountOperateRecord(id, userId, type, status, time);
    }

    @Override
    public boolean updateOperate(OperateRecord operateRecord) {
        int i = operateRecordMapper.updateByPrimaryKeySelective(operateRecord);
        if (i > 0) {
            return true;
        }
        return false;
    }
}
