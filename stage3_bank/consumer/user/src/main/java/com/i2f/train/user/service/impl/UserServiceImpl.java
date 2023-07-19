package com.i2f.train.user.service.impl;

import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.common.util.AESUtil;
import com.i2f.train.starter.common.util.JwtUtils;
import com.i2f.train.starter.common.util.RegexUtil;
import com.i2f.train.starter.model.Account;
import com.i2f.train.starter.model.DTO.UserDto;
import com.i2f.train.starter.model.User;
import com.i2f.train.starter.model.UserAccountRelation;
import com.i2f.train.starter.model.UserFriends;
import com.i2f.train.starter.model.vo.BindCardVO;
import com.i2f.train.starter.model.vo.FinanceHoldVO;
import com.i2f.train.starter.model.vo.FinanceOrderVO;
import com.i2f.train.starter.model.vo.UserVo;
import com.i2f.train.user.feign.AccountFeign;
import com.i2f.train.user.feign.FinanceFeign;
import com.i2f.train.user.feign.UserFeign;
import com.i2f.train.user.service.UserService;
import io.jsonwebtoken.Jwt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author: 刘志亮
 * @date: 2022/3/30 15:23
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserFeign userFeign;
    @Resource
    AccountFeign accountFeign;
    @Autowired
    FinanceFeign financeFeign;
    @Resource
    private RedisTemplate redisTemplate;
    /**
     * 签到
     * @return
     */
    @Override
    public Result signIn() {
//        从token获取userId
        String userId = JwtUtils.getUserId();
        //从数据库查询该用户信息
        User user = userFeign.selectByPrimaryKey(userId);
        //获得系统当前时间
        Date date=new Date();
        //如果上次签到时间为空
        if (user.getSignTime()==null){
            //修改签到时间和积分
            user.setSignTime(date);
            user.setIntegral(user.getIntegral()+100);
            int i = userFeign.updateByPrimaryKeySelective(user);
            //修改失败
            if (i==0){
                return  Result.error(CommonConstants.Request.INTERNAL_ERROR,CommonConstants.Request.INTERNAL_ERROR_MESSAGE);
            }
            //从数据库查询该用户信息
            User users = userFeign.selectByPrimaryKey(userId);
            return  Result.success(users.getIntegral(),CommonConstants.Request.SUCCESS,"签到成功");
        }
        //判断今天是否已经签到了
        int date1 = user.getSignTime().getDate();
        int date2 = date.getDate();
        if (date1==date2&&user.getSignTime().getMonth()==date.getMonth()&&user.getSignTime().getYear()==date.getYear()){
            return Result.error(CommonConstants.Request.SIGIN_REPEAT_ERROR,CommonConstants.Request.SIGIN_REPEAT_ERROR_MESSAGE);
        }
        //修改签到时间和积分
        user.setSignTime(date);
        user.setIntegral(user.getIntegral()+100);
        int i = userFeign.updateByPrimaryKeySelective(user);
        //修改失败
        if (i==0){
            return  Result.error(CommonConstants.Request.INTERNAL_ERROR,CommonConstants.Request.INTERNAL_ERROR_MESSAGE);
        }
        //从数据库查询该用户信息
        User users = userFeign.selectByPrimaryKey(userId);
        return  Result.success(users.getIntegral(),CommonConstants.Request.SUCCESS,"签到成功");

    }

    /**注册*/
    @Override
    public Result register(UserVo userVo) {
        int result=0;
        String phoneCode=(String) redisTemplate.opsForValue().get(userVo.getPhone());
        if (phoneCode == null){
            return Result.error(CommonConstants.Request.CODE_FAILED,CommonConstants.Request.CODE_MESSAGE);
        }
        if((userVo.getPhoneCode()).equals(phoneCode)){
            boolean b = RegexUtil.validateMobilePhone(userVo.getPhone());
            User users =userFeign.findByPhone(userVo.getPhone());

            if(users!=null){
                return Result.error(CommonConstants.Request.REGISTER_FAILED,CommonConstants.Request.REGISTER_USER_MESSAGE);
            }
            if(StringUtils.isBlank(userVo.getPhone())){
                return Result.error(CommonConstants.Request.REGISTER_FAILED,CommonConstants.Request.REGISTER_PHONENULL_MESSAGE);
            }
            if (b==false) {
                return Result.error(CommonConstants.Request.REGISTER_FAILED,CommonConstants.Request.REGISTER_PHONE_MESSAGE);
            }
            if(StringUtils.isBlank(userVo.getPassword())) {
                return Result.error(CommonConstants.Request.REGISTER_FAILED,CommonConstants.Request.REGISTER_PASSWORD_MESSAGE);
            }
            result=userFeign.addUser(userVo);
            if (result == 1) {
                return Result.success(CommonConstants.Request.SUCCESS,CommonConstants.Request.REGISTER_SUCCESS_MESSAGE);
            } else {
                return Result.error(CommonConstants.Request.REGISTER_FAILED,CommonConstants.Request.REGISTER_FAILED_MESSAGE);
            }

        }else{
            return Result.error(CommonConstants.Request.CODE_FAILED,CommonConstants.Request.CODE_FAILED_MESSAGE);
        }
    }

    /**密码登录*/
    @Override
    public Result loginByPassword(UserVo userVo) {
        String imageCode = (String) redisTemplate.opsForValue().get(userVo.getImageId());
        if (imageCode == null) {
            return Result.error(CommonConstants.Request.CODE_FAILED, CommonConstants.Request.CODE_MESSAGE);
        }
        if (StringUtils.isBlank(userVo.getImageCode()) || !(imageCode.equalsIgnoreCase(userVo.getImageCode()))) {
            return Result.error(CommonConstants.Request.CODE_FAILED, CommonConstants.Request.CODE_FAILED_MESSAGE);
        }
        User user = userFeign.findByPhone(userVo.getPhone());
        if (user == null) {
            return Result.error(CommonConstants.Request.LOGIN_FAILED, CommonConstants.Request.LOGIN_USER_MESSAGE);
        }

        User loginUser = userFeign.judgeUserInfo(userVo);
        if (loginUser != null) {
            JwtUtils.createToken(loginUser.getId());
            String token = JwtUtils.createToken(user.getId());
            boolean bind = isBind(user.getId());
            if (bind){
                user.setSex("1");
            }
            UserDto userDto = loginUser.toDto();
            userDto.setToken(token);
            return Result.success(userDto, CommonConstants.Request.SUCCESS, CommonConstants.Request.LOGIN_SUCCESS_MESSAGE);
        } else {
            return Result.error(CommonConstants.Request.LOGIN_FAILED, CommonConstants.Request.LOGIN_PASSWORD_MESSAGE);
        }
    }

    /**手机登录*/
    @Override
    public Result loginByPhone(UserVo userVo) {
        String vioCode = redisTemplate.opsForValue().get(userVo.getPhone()) == null? "": (String) redisTemplate.opsForValue().get(userVo.getPhone());
        if(StringUtils.isBlank(vioCode)){
            return Result.error(CommonConstants.Request.CODE_FAILED,CommonConstants.Request.    CODE_MESSAGE);
        }
        if(StringUtils.isBlank(userVo.getPhoneCode())|| !(vioCode. equalsIgnoreCase(userVo.getPhoneCode()))){
            return Result.error(CommonConstants.Request.CODE_FAILED,CommonConstants.Request.CODE_FAILED_MESSAGE);
        }
        User user = userFeign.findByPhone(userVo.getPhone());
        if(user!=null){
            String token = JwtUtils.createToken(user.getId());user.setIsDeleted(token);
            boolean bind = isBind(user.getId());
            if (bind){
                user.setSex("1");
            }
            UserDto userDto = user.toDto();
            userDto.setToken(token);
            return Result.success(userDto,CommonConstants.Request.SUCCESS,CommonConstants.Request.LOGIN_SUCCESS_MESSAGE);
        }
        else{
            return Result.error(CommonConstants.Request.LOGIN_FAILED,CommonConstants.Request.LOGIN_USER_MESSAGE);
        }
    }

    /**修改手机号*/
    @Override
    public Result upPhone(UserVo userVo) {
        String phoneCode=(String) redisTemplate.opsForValue().get(userVo.getPhone());
        if (StringUtils.isBlank(phoneCode)){
            return Result.error(CommonConstants.Request.CODE_FAILED,CommonConstants.Request.CODE_MESSAGE);
        }
        if(StringUtils.isBlank(userVo.getPhoneCode()) || !(phoneCode.equals(userVo.getPhoneCode()))){
            return Result.error(CommonConstants.Request.CODE_FAILED,CommonConstants.Request.CODE_FAILED_MESSAGE);
        }
        User byPhone = userFeign.findByPhone(userVo.getPhone());
        if(byPhone!=null){
            return Result.error(CommonConstants.Request.MOBILE_PHONE_MODIFY_FAIL,"该手机号已经被注册！");
        }
        String userId = JwtUtils.getUserId();
        userVo.setId(userId);
        int ok=userFeign.upPhone(userVo);
        if(ok>0){
            return Result.success(CommonConstants.Request.SUCCESS,CommonConstants.Request.SUCCESS_MESSAGE);
        }else{
            return Result.error(CommonConstants.Request.MOBILE_PHONE_MODIFY_FAIL,CommonConstants.Request.MOBILE_PHONE_MODIFY_FAIL_MESSAGE);
        }
    }

    /**修改登录密码*/
    @Override
    public Result upPassword(UserVo userVo) {
        String phoneCode = (String) redisTemplate.opsForValue().get(userVo.getPhone());
        if(StringUtils.isEmpty(phoneCode)){
            return Result.error(CommonConstants.Request.CODE_FAILED,CommonConstants.Request.CODE_MESSAGE);
        }
        if(!userVo.getPhoneCode().equals(phoneCode)){
            return Result.error(CommonConstants.Request.CODE_FAILED,CommonConstants.Request.CODE_FAILED_MESSAGE);
        }
        User byPhone = userFeign.findByPhone(userVo.getPhone());
        if (byPhone == null){
            return Result.error(CommonConstants.Request.FAILED, "用户不存在");
        }
        userVo.setId(byPhone.getId());

        int ok=userFeign.upPassword(userVo);
        if(ok>0){
            return Result.success(CommonConstants.Request.SUCCESS,CommonConstants.Request.SUCCESS_MESSAGE);
        }else{
            return Result.error(CommonConstants.Request.FAILED,CommonConstants.Request.FAILED_MESSAGE);
        }
    }

    /**修改支付密码*/
    @Override
    public Result upPayPassword(UserVo userVo) {
        String phoneCode = (String) redisTemplate.opsForValue().get(userVo.getPhone());
        if(StringUtils.isEmpty(phoneCode)){
            return Result.error(CommonConstants.Request.CODE_FAILED,CommonConstants.Request.CODE_MESSAGE);
        }
        if(!userVo.getPhoneCode().equals(phoneCode)){
            return Result.error(CommonConstants.Request.CODE_FAILED,CommonConstants.Request.CODE_FAILED_MESSAGE);
        }
        //修改user表的支付密码
        String userId = JwtUtils.getUserId();
        userVo.setId(userId);
        int okUser=userFeign.upPayPassword(userVo);
        String accountId = userFeign.selectByPrimaryKey(userVo.getId()).getAccountId();
        //修改account表的支付密码
        userVo.setAccountId(accountId);
        int okAccount=accountFeign.upAccountPayPassword(userVo);
        if(okUser>0&&okAccount>0){
            return Result.success(CommonConstants.Request.SUCCESS,CommonConstants.Request.SUCCESS_MESSAGE);
        }else{
            return Result.error(CommonConstants.Request.FAILED,CommonConstants.Request.FAILED_MESSAGE);
        }
    }

    /**注销用户*/
    @Override
    public Result writeOffUser(UserVo userVo) {

        String userId = JwtUtils.getUserId();
        if (StringUtils.isAnyBlank(userId,userVo.getPhone(),userVo.getPhoneCode())){
            return Result.error(CommonConstants.Request.WRITE_OFF_FAIL, CommonConstants.Request.WRITE_OFF_FAIL_MESSAGE);
        }
        String phoneCode = (String) redisTemplate.opsForValue().get(userVo.getPhone());
        if (!userVo.getPhoneCode().equals(phoneCode)){
            return Result.error(CommonConstants.ValidateCode.PHONE_CODE_ERROR, CommonConstants.ValidateCode.VALIDATE_ERROR_MESSAGE);
        }
        //获取用户账户余额
        User user = userFeign.findByPhone(userVo.getPhone());
        String userAccount = user.getAccountId();
        Account account = accountFeign.selectAccountId(userAccount);
        BigDecimal money = new BigDecimal(account.getMoney());
        if (BigDecimal.ZERO.compareTo(money) == -1){
            return Result.error(CommonConstants.Request.WRITE_OFF_FAIL, "账户余额不为0，注销失败！");
        }
        //获取用户持仓列表
        List<FinanceHoldVO> financeHoldVOS = financeFeign.selectFinanceHold(userId);
        if (financeHoldVOS == null || !financeHoldVOS.isEmpty()){
            return Result.error(CommonConstants.Request.WRITE_OFF_FAIL, "您还存在基金持仓，注销失败！");
        }
        //获取用户正在购买中的记录
        List<FinanceOrderVO> order = financeFeign.getOrder(userId,"2");
        for (FinanceOrderVO financeOrderVO : order) {
            if ("2".equals(financeOrderVO.getStatus())){
                return Result.error(CommonConstants.Request.WRITE_OFF_FAIL, "您还存在交易中的理财产品，注销失败！");
            }
        }
        //----------->注销用户
        //删除用户关系
        UserFriends userFriends = new UserFriends();
        userFriends.setUserId(userId);
        Result friendResult = userFeign.deleteAll(userFriends);
        if(friendResult == null || !CommonConstants.Request.SUCCESS.equals(friendResult.getCode()) && !"0008".equals(friendResult.getCode())){
            return Result.error(CommonConstants.Request.WRITE_OFF_FAIL, CommonConstants.Request.WRITE_OFF_FAIL_MESSAGE);
        }
        //解绑用户所有卡
        Result cardResult = userFeign.removeAllCard(userId);
        if(cardResult == null || !CommonConstants.Request.SUCCESS.equals(cardResult.getCode())){
            return Result.error(CommonConstants.Request.WRITE_OFF_FAIL, CommonConstants.Request.WRITE_OFF_FAIL_MESSAGE);
        }
        //删除用户电子账户
        Result accountResult = userFeign.removeCard(userAccount, userId);
        if(accountResult == null || !CommonConstants.Request.SUCCESS.equals(accountResult.getCode())){
            return Result.error(CommonConstants.Request.WRITE_OFF_FAIL, CommonConstants.Request.WRITE_OFF_FAIL_MESSAGE);
        }
        //删除用户信息
        int i = userFeign.deleteUser(userId);
        if(1 != i){
            return Result.error(CommonConstants.Request.WRITE_OFF_FAIL, CommonConstants.Request.WRITE_OFF_FAIL_MESSAGE);
        }
        return Result.success(CommonConstants.Request.SUCCESS, CommonConstants.Request.SUCCESS_MESSAGE);
    }

    /**
     * 通过用户Id去数据库查询用户信息
     * @param map
     * @return User
     */
    @Override
    public User selectById(Map<String,String> map) {
        String id = map.get("id");
        return userFeign.selectByPrimaryKey(id);
    }

    /**
     * 更换皮肤
     * @param skinId
     * @return
     */
    @Override
    public Result ReplacementSkin(String skinId) {

        //从token获取userId
        String userId = JwtUtils.getUserId();
        if (StringUtils.isBlank(skinId)){
            return Result.error(CommonConstants.Request.INTERNAL_ERROR,CommonConstants.Request.INTERNAL_ERROR_MESSAGE);
        }
        User user = userFeign.selectByPrimaryKey(userId);
        user.setSkinId(skinId);
        int i = userFeign.updateByPrimaryKeySelective(user);
        if (i!=1){
            return Result.error(CommonConstants.Request.INTERNAL_ERROR,CommonConstants.Request.INTERNAL_ERROR_MESSAGE);
        }
        User user2 = userFeign.selectByPrimaryKey(userId);
        return Result.success(user2.getSkinId(),CommonConstants.Request.SUCCESS,"更换成功");

    }

    /**
     * 更换头像
     * @param
     * @return
     */

    @Override
    public Result ReplacementHead(String imageId) {

        //从token获取userId
        String userId = JwtUtils.getUserId();
        if (StringUtils.isBlank(imageId)){
            return Result.error(CommonConstants.Request.INTERNAL_ERROR,CommonConstants.Request.INTERNAL_ERROR_MESSAGE);
        }
        //更换头像
        User user = userFeign.selectByPrimaryKey(userId);
        user.setImageId(imageId);
        int i = userFeign.updateByPrimaryKeySelective(user);
        if (i!=1){
            return Result.error(CommonConstants.Request.INTERNAL_ERROR,CommonConstants.Request.INTERNAL_ERROR_MESSAGE);
        }
        User user2 = userFeign.selectByPrimaryKey(userId);
        return Result.success(user2.getImageId(),CommonConstants.Request.SUCCESS,"更换成功");

    }

    /**
     * 解除银行卡绑定
     * @param bindCardVO
     * @return Result
     */
    @Override
    public Result removeCard(BindCardVO bindCardVO) {
        //从token获取userId
        String userId = JwtUtils.getUserId();
        //从redis中获取手机验证码
        String rightCode = (String) redisTemplate.opsForValue().get(bindCardVO.getPhone());
         //判断是否已经发验证码给该手机
         if (StringUtils.isBlank(rightCode)) {
         return Result.error(CommonConstants.VerificationCode.NOT_GENERATE_CODE, CommonConstants.VerificationCode.NOT_GENERATE_MESSAGE);
         }
         //判断验证码是否正确
         if (!StringUtils.equals(rightCode, bindCardVO.getUserCode())) {
         return Result.error(CommonConstants.VerificationCode.IMPUTE_ERROR_CODE, CommonConstants.VerificationCode.IMPUTE_ERROR_MESSAGE);
         }
        return userFeign.removeCard(bindCardVO.getAccountId(),userId);
    }

    @Override
    public Result setRoleType(String type) {
        User user = new User();
        String userId = JwtUtils.getUserId();
        if(StringUtils.isAnyBlank(userId,type)){
            return Result.error(CommonConstants.Request.EVALUATE_FAIL, CommonConstants.Request.EVALUATE_FAIL_MESSAGE);
        }
        user.setId(userId);
        user.setEmail(type);
        int i = userFeign.updateByPrimaryKeySelective(user);
        if (1 == i){
            return Result.success(null);
        }
        return Result.error(CommonConstants.Request.EVALUATE_FAIL, CommonConstants.Request.EVALUATE_FAIL_MESSAGE);
    }

    @Override
    public Result getUserInfo() {
        String userId = JwtUtils.getUserId();
        User user = userFeign.selectByPrimaryKey(userId);
        if (user == null){
            return Result.error(CommonConstants.Request.FAILED,"用户信息获取失败！");
        }
        boolean bind = isBind(user.getId());
        if (bind){
            user.setSex("1");
        }
        return Result.success(user.toDto());
    }

    @Override
    public Result unbound(String userId, String accountId, String payPassword) {
        UserAccountRelation userAccountRelation = userFeign.selectByAccountAndId(userId, accountId);
        if (userAccountRelation == null) {
            return Result.error(CommonConstants.unbound.ACCOUNT_ERROR, CommonConstants.unbound.ACCOUNT_ERROR_MESSAGE);
        }
        Account account = accountFeign.selectAccountId(accountId);
        if (!account.getPayPassword().equals(payPassword)) {
            return Result.error(CommonConstants.unbound.PASSWORD_ERROR, CommonConstants.unbound.PASSWORD_ERROR_MESSAGE);
        }
        userFeign.removeCard(accountId, userId);
        return Result.success(CommonConstants.Request.SUCCESS);
    }

    /**
     * 判断用户是否绑卡
     * @param id
     * @return
     */
    public boolean isBind(String id){
        List<UserAccountRelation> relations = userFeign.getRelations(id);
        if (relations == null || relations.isEmpty()){
            return false;
        }
        return true;
    }


}
