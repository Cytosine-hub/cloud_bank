package com.i2f.train.user.service;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.model.User;
import com.i2f.train.starter.model.vo.BindCardVO;
import com.i2f.train.starter.model.vo.UserVo;

import java.util.Map;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 15:23
 */
public interface UserService {


    /**
     * 签到
     * @return Result；
     */
    Result signIn();

    /**注册*/
    Result register(UserVo userVo);

    /**密码登录*/
    Result loginByPassword(UserVo userVo);

    /**手机登录*/
    Result loginByPhone(UserVo userVo);

    /**修改手机号*/
    Result upPhone(UserVo userVo);

    /**修改登录密码*/
    Result upPassword(UserVo userVo);

    /**修改支付密码*/
    Result upPayPassword(UserVo userVo);

    /**注销用户*/
    Result writeOffUser(UserVo userVo);

    /**
     * 通过用户Id去数据库查询用户信息
     * @param map
     * @return User
     */
    User selectById(Map<String, String> map);

    /**
     * 更换皮肤
     * @param  skinId
     * @return
     */
    Result ReplacementSkin(   String skinId);

    /**
     * 更换头像
     *
     * @param imageId
     * @return
     */
    Result ReplacementHead(String imageId);

    /**
     * 解除银行卡绑定
     * @param bindCardVO
     * @return Result
     */
    Result removeCard(BindCardVO bindCardVO);

    /**
     * 设定用户理财类型
     * @param type 用户类型
     * @return
     */
    Result setRoleType(String type);

    Result unbound(String userId, String accountId, String payPassword);

    /**
     * 获取用户信息
     * @return
     */
    Result getUserInfo();
}
