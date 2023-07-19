package com.i2f.train.userProvider.service;

import com.i2f.train.starter.model.User;
import com.i2f.train.starter.model.vo.UserVo;
import com.i2f.train.starter.model.vo.OpenAccountVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 13:56
 */
public interface UserService {
    User selectUserById(String userId);

    /**
     * 通过用户id修改用户部分信息
     * @param user
     * @return
     */
    int updateByPrimaryKeySelective(User user);
    User selectById(String id);

    /**
     * 修改用户信息
     * @param user
     * @return boolean
     */
    boolean update(User user);

    User findByPhone(String mobilePhone);
    int addUser(UserVo userVo);

    /**
     * 根据用户id修改用户信息
     * @param openAccountVo
     * @param userId
     * @param accountId
      * @return boolean
     */
    boolean updateByUserId(OpenAccountVo openAccountVo, String userId,String accountId);

    User judgeUserInfo(UserVo userVo);

    int upPhone(UserVo userVo);

    int upPassword(UserVo userVo);

    int upPayPassword(UserVo userVo);

    int deleteUser(String userId);

    int deleteUserAccountRelation(String userId);
    int deleteAllUserFriend(String userId);
}
