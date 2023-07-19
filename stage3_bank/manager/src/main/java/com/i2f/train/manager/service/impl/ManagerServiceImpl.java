package com.i2f.train.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.i2f.train.manager.common.constants.ManagerConstants;
import com.i2f.train.manager.mapper.ManagerMapper;
import com.i2f.train.manager.model.Manager;
import com.i2f.train.manager.model.dto.ManagerDto;
import com.i2f.train.manager.model.queryMap.ManagerQueryMap;
import com.i2f.train.manager.model.vo.ManagerVo;
import com.i2f.train.manager.service.FileService;
import com.i2f.train.manager.service.FinanceItemService;
import com.i2f.train.manager.service.ManagerService;
import com.i2f.train.manager.service.ProductionService;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.common.util.UUIDGenerator;
import com.i2f.train.starter.model.FinanceItem;
import com.i2f.train.starter.model.Production;
import com.i2f.train.starter.model.queryMap.ProductionQueryMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 11:18
 */
@Service
public class ManagerServiceImpl implements ManagerService {
    @Resource
    ManagerMapper managerMapper;
    @Resource
    UUIDGenerator uuidGenerator;
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    FileService fileService;
    @Resource
    ProductionService productionService;
    @Resource
    FinanceItemService financeItemService;

    /**
     * 不能添加比自己权限高的管理员
     *
     * @param manager
     * @return
     */

    @Override
    public Result addNewManager(Manager manager) {
        //判断前端传来的数据是否为空
        if (manager.getName().isEmpty() || manager.getMobilePhone().isEmpty() || manager.getLevel().isEmpty() || manager.getPassword().isEmpty()) {
            return Result.error(ManagerConstants.Code.ADD_DEFICIENCY_FAILED, ManagerConstants.Message.ADD_DEFICIENCY_FAILED_MESSAGE);
        }

        //当前用户权限
        ManagerDto loginMsg = (ManagerDto) redisTemplate.opsForValue().get("loginMsg");
        if(loginMsg == null){
            return Result.error(ManagerConstants.Code.NOT_LOGIN, ManagerConstants.Message.NOT_LOGIN_MESSAGE);
        }
        String level = loginMsg.getLevel();
        //判断权限
        if (Integer.parseInt(manager.getLevel()) <= Integer.parseInt(level)) {
            return Result.error(ManagerConstants.Code.LEVEL_ERROR, "你没有权限增加比你权限高或一样的管理员");
        }
        Manager current = managerMapper.findByPhone(manager.getMobilePhone());
        if (current != null) {
            return Result.error(ManagerConstants.Code.ADD_FAILED, "当前管理员已添加");
        }
        String id = uuidGenerator.getId();
        manager.setId(id);
        int i = managerMapper.insertSelective(manager);
        if (i == 1) {
            return Result.success(ManagerConstants.Code.SUCCESS, ManagerConstants.Message.SUCCESS_MESSAGE);
        }
        return Result.error(ManagerConstants.Code.ADD_FAILED, ManagerConstants.Message.ADD_FAILED_MESSAGE);
    }

    @Override
    public Result login(ManagerVo managerVo) {
        if (StringUtils.isBlank(managerVo.getMobilePhone())) {
            return Result.error(ManagerConstants.Code.LOGIN_FAILED, "手机号不能为空");
        }
        if (StringUtils.isBlank(managerVo.getPassword())) {
            return Result.error(ManagerConstants.Code.LOGIN_FAILED, "密码不能为空");
        }
        Manager manager = managerMapper.findByPhone(managerVo.getMobilePhone());
        if (manager == null) {
            return Result.error(ManagerConstants.Code.LOGIN_FAILED, "登陆失败");
        }
        if (!managerVo.getPassword().equals(manager.getPassword())) {
            return Result.error(ManagerConstants.Code.LOGIN_FAILED, "密码错误");
        }
        //保存用户信息
        redisTemplate.opsForValue().set("loginMsg", manager.toDto(), 1, TimeUnit.DAYS);
        return Result.success(manager.toDto(), ManagerConstants.Code.SUCCESS, ManagerConstants.Message.SUCCESS_MESSAGE);
    }

    /**
     * 不能修改自己的权限，也不能修改比自己高的管理员信息
     *
     * @param map 包括管理员id 和 修改后 的等级
     * @return
     */
    @Override
    public Result levelChange(Map<String, String> map) {
        String id = map.get("id");
        String level = map.get("level");

        ManagerDto loginMsg = (ManagerDto) redisTemplate.opsForValue().get("loginMsg");
        if(loginMsg == null){
            return Result.error(ManagerConstants.Code.NOT_LOGIN, ManagerConstants.Message.NOT_LOGIN_MESSAGE);
        }
        String phone = loginMsg.getMobilePhone();
        String currentLevel = loginMsg.getLevel();

        if (StringUtils.isBlank(id)) {
            return Result.error(ManagerConstants.Code.LEVEL_ERROR, "被操作人的ID不能为空");
        }
        if (StringUtils.isBlank(level)) {
            return Result.error(ManagerConstants.Code.LEVEL_ERROR, "修改后的等级不能为空");
        }
        Manager manager = managerMapper.selectByPrimaryKey(id);
        if (manager.getMobilePhone().equals(phone)) {
            return Result.error(ManagerConstants.Code.LEVEL_ERROR, "不能修改自己的权限");
        }
        //判断权限
        if (Integer.parseInt(currentLevel) >= Integer.parseInt(level)) {
            return Result.error(ManagerConstants.Code.LEVEL_ERROR, "权限不足，修改失败");
        }
        int i = managerMapper.updateLevelById(level, id);
        if (i == 1) {
            return Result.success(ManagerConstants.Code.SUCCESS, ManagerConstants.Message.SUCCESS_MESSAGE);
        }
        return Result.error(ManagerConstants.Code.LEVEL_ERROR, "修改失败！");
    }

    /**
     * 不能删除自己和比自己权限高的管理员
     *
     * @param id
     * @return
     */
    @Override
    public Result delete(String id) {
        Manager manager = managerMapper.selectByPrimaryKey(id);
        ManagerDto loginMsg = (ManagerDto) redisTemplate.opsForValue().get("loginMsg");
        if(loginMsg == null){
            return Result.error(ManagerConstants.Code.NOT_LOGIN, ManagerConstants.Message.NOT_LOGIN_MESSAGE);
        }
        String phone = loginMsg.getMobilePhone();
        String level = loginMsg.getLevel();
        if (manager.getMobilePhone().equals(phone)) {
            return Result.error(ManagerConstants.Code.DELETE_PHONE_FAILED, ManagerConstants.Message.DELETE_PHONE_FAILED_MESSAGE);
        }
        if (Integer.parseInt(manager.getLevel()) <= Integer.parseInt(level)) {
            return Result.error(ManagerConstants.Code.DELETE_LEVEL_FAILED, ManagerConstants.Message.DELETE_LEVEL_FAILED_MESSAGE);
        }
        int i = managerMapper.deleteByPrimaryKey(id);
        if (i == 1) {
            return Result.success(ManagerConstants.Code.SUCCESS, ManagerConstants.Message.SUCCESS_MESSAGE);
        }
        return Result.error(ManagerConstants.Code.DELETE_FAILED, ManagerConstants.Message.DELETE_FAILED_MESSAGE);
    }

    @Override
    public Result queryManagers(ManagerQueryMap queryMap) {
        //分页插件核心代码 一定要先配置分页 再查询
        PageHelper.startPage(queryMap.getPage(), queryMap.getPageSize());
        List<ManagerDto> managerDto = managerMapper.queryManagers(queryMap);
        if (managerDto == null) {
            return Result.error(ManagerConstants.Code.QUERY_FAILED, ManagerConstants.Message.QUERY_FAILED_MESSAGE);
        }
        PageInfo<ManagerDto> pageInfo = new PageInfo<>(managerDto);

        return Result.success(pageInfo, ManagerConstants.Code.SUCCESS, ManagerConstants.Message.SUCCESS_MESSAGE);
    }

    /**
     * 修改管理员个人信息
     *
     * @param manager
     * @return
     */
    @Override
    public Result modify(Manager manager) {
        if (StringUtils.isBlank(manager.getId())) {
            return Result.error(ManagerConstants.Code.MODIFY_FAILED, "ID不能为空");
        }
        if (StringUtils.isNotBlank(manager.getLevel())) {
            return Result.error(ManagerConstants.Code.LEVEL_ERROR, "不能修改自己的权限");
        }
        int i = managerMapper.updateByPrimaryKeySelective(manager);
        if (i == 1) {
            return Result.success(ManagerConstants.Code.SUCCESS, ManagerConstants.Message.SUCCESS_MESSAGE);
        }
        return Result.error(ManagerConstants.Code.MODIFY_FAILED, ManagerConstants.Message.MODIFY_FAILED_MESSAGE);
    }

    @Override
    public Result getTotal() {
        Map countMap = new HashMap();
        //统计文件数量
        Map<String, Integer> fileCount = fileService.countFile();
        Map<String, Integer> productionCount = new HashMap<>();
        Map<String, Integer> financeItemCount = new HashMap<>();

        //统计积分产品数量
        Result result = productionService.selectByAnyField(new Production());
        List<Production> productionList = (List<Production>) result.getData();
        if (productionList == null) {
            return Result.error();
        }
        Result<List<FinanceItem>> listResult = financeItemService.queryAll();
        List<FinanceItem> financeItemList = listResult.getData();

        IntSummaryStatistics intSummaryStatistics = financeItemList.stream().mapToInt(FinanceItem::intType).summaryStatistics();

        financeItemCount.put("advance", (int) intSummaryStatistics.getSum());
        financeItemCount.put("primary", (int) (intSummaryStatistics.getCount() - intSummaryStatistics.getSum()));
        productionCount = filterProductionList(productionList);
        countMap.put("finance", financeItemCount);
        countMap.put("production", productionCount);
        countMap.put("file", fileCount);
        return Result.success(countMap);
    }

    public Map<String, Integer> filterProductionList(List<Production> list) {
        Map<String, Integer> resultMap = new HashMap<>();
        for (Production production : list) {
            if ("1".equals(production.getState())) {
                resultMap.put("up", 0);
            } else {
                resultMap.put("down", 0);
            }
        }
        for (Production production : list) {
            if ("1".equals(production.getState())) {
                Integer integer = resultMap.get("up");
                resultMap.put("up", integer + 1);
            } else {
                Integer integer = resultMap.get("down");
                resultMap.put("down", integer + 1);
            }

        }
        return resultMap;
    }

}
