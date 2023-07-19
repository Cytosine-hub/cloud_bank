package com.i2f.train.manager.controller;

import com.i2f.train.manager.model.Manager;
import com.i2f.train.manager.model.queryMap.ManagerQueryMap;
import com.i2f.train.manager.model.vo.ManagerVo;
import com.i2f.train.manager.service.ManagerService;
import com.i2f.train.starter.common.model.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 11:17
 */
@RestController
@RequestMapping("/manager")
public class ManagerController{

    @Resource
    ManagerService managerService;

    @PostMapping("/add")
    public Result addManager(@RequestBody Manager manager) {
        return managerService.addNewManager(manager);
    }

    @PostMapping("/login")
    public Result login(@RequestBody ManagerVo managerVo) {
        return managerService.login(managerVo);
    }

    @PostMapping("/levelChange")
    public Result leveChange(@RequestBody Map<String, String> map) {
        return managerService.levelChange(map);
    }

    @GetMapping("/delete")
    public Result delete(String id) {
        return managerService.delete(id);
    }

    @PostMapping("/query")
    public Result query(@RequestBody ManagerQueryMap queryMap) {
        return managerService.queryManagers(queryMap);
    }

    @PostMapping("/modify")
    public Result modify(@RequestBody Manager manager) {
        return managerService.modify(manager);
    }

    @GetMapping("/count")
    public Result getTotalNum(){
        return managerService.getTotal();
    }
}
