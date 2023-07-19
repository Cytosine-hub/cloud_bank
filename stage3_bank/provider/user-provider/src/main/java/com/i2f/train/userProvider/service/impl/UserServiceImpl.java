package com.i2f.train.userProvider.service.impl;

import com.i2f.train.starter.common.util.UUIDUtils;
import com.i2f.train.starter.common.util.UUIDGenerator;
import com.i2f.train.starter.model.User;
import com.i2f.train.starter.model.vo.UserVo;
import com.i2f.train.starter.model.vo.OpenAccountVo;
import com.i2f.train.userProvider.mapper.UserMapper;
import com.i2f.train.userProvider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 13:57
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    UUIDGenerator uuidGenerator;

    /**
     * 通过用户Id查询用户信息
     * @param
     * @return
     */
    @Override
    public User selectUserById(String userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user;
    }

    @Override
    public User selectById(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改用户信息
     * @param user
     * @return boolean
     */
    @Override
    public boolean update(User user) {
        int ok = userMapper.updateByPrimaryKeySelective(user);
        if (ok > 0){
            return true;
        }
        return false;
    }

    @Override
    public User findByPhone(String mobilePhone) {
        return userMapper.findByPhone(mobilePhone);
    }

    @Override
    public int addUser(UserVo userVo) {
        String uuid = uuidGenerator.getId();
        //调用工具类操作数据库，获取信息
        User user=new User();
        user.setId(uuid);
        user.setPhone(userVo.getPhone());
        user.setPassword(userVo.getPassword());
        return userMapper.insertSelective(user);
    }

    /**
     * 修改用户信息
     * @param openAccountVo
     * @param userId
     * @return
     */

    @Override
    public boolean updateByUserId(OpenAccountVo openAccountVo, String userId,String accountId) {
        User user = new User();
        user.setId(userId);
        user.setAccountId(accountId);
        user.setName(openAccountVo.getUsername());
        user.setPayPassword(openAccountVo.getPayPassword());
        user.setPersonalId(openAccountVo.getPersonalId());
        int i = userMapper.updateByPrimaryKeySelective(user);
        if (i>0){
            return true;
        }
        return false;
    }

    /**
     * 通过用户id修改用户部分信息
     * @param user
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User judgeUserInfo(UserVo userVo) {
        //登录验证用户信息
        User user = userMapper.findByPhone(userVo.getPhone());
        //判断User是否为空
        if (user == null){
            return null;
        }
        String userPassword = user.getPassword();
        if (!userVo.getPassword().equals(userPassword)){
            return null;
        }
        return user;
    }

    @Override
    public int upPhone(UserVo userVo) {
        User user=userVo.toUser();
        int ok=userMapper.updateByPrimaryKeySelective(user);
        return ok;
    }

    @Override
    public int upPassword(UserVo userVo) {
        User user = userVo.toUser();
        user.setPassword(userVo.getNewPassword());
        int ok=userMapper.updateByPrimaryKeySelective(user);
        return ok;
    }

    @Override
    public int upPayPassword(UserVo userVo) {
        User user=userVo.toUser();
        user.setPayPassword(userVo.getNewPassword());
        int ok=userMapper.updateByPrimaryKeySelective(user);
        return ok;
    }

    @Override
    public int deleteUser(String userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int deleteUserAccountRelation(String userId) {
        return userMapper.deleteUserAccountRelation(userId);
    }

    @Override
    public int deleteAllUserFriend(String userId) {
        return userMapper.deleteAllUserFriend(userId);
    }


}
