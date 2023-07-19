package com.i2f.train.userProvider.controller;

import com.i2f.train.starter.model.OperateRecord;
import com.i2f.train.userProvider.service.OperateRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 刘志亮
 * @date: 2022/3/31 10:30
 */
@RestController
@RequestMapping("/operateRecord")
public class OperateRecordController {
    @Resource
    OperateRecordService operateRecordService;

    /**
     * 增加一条操作记录
     * @param operateRecord
     * @return boolean
     */
    @PostMapping("/operateRecordAdd")
    public boolean operateRecordAdd(@RequestBody OperateRecord operateRecord){
        return operateRecordService.operateRecordAdd(operateRecord);
    }

    /**
     * 修改可疑记录
     * @param id
     * @param success
     * @return
     */
    @RequestMapping("/updateOperateRecord")
    public boolean updateOperateRecord(@RequestParam String id, @RequestParam String success){
        return operateRecordService.updateOperateRecord(id,success);
    }

    @RequestMapping("/updateOperate")
    public boolean updateOperate(@RequestBody OperateRecord operateRecord) {
        return operateRecordService.updateOperate(operateRecord);
    }
}
