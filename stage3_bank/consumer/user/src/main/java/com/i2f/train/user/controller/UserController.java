package com.i2f.train.user.controller;

import com.i2f.train.starter.common.model.Result;
import com.i2f.train.starter.common.util.JwtUtils;
import com.i2f.train.starter.model.User;
import com.i2f.train.starter.model.vo.UnboundVO;
import com.i2f.train.starter.model.vo.UserVo;
import com.i2f.train.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 15:10
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    /**用户注册*/
    @RequestMapping("/register")
    public Result register(@RequestBody UserVo userVo)  {
        return userService.register(userVo);
    }

    @GetMapping("/userInfo")
    public Result getUserInfo(){
        return userService.getUserInfo();
    }

    /**密码登录*/
    @RequestMapping("/loginByPassword")
    public Result loginByPassword(@RequestBody UserVo userVo) {
        return userService.loginByPassword(userVo);
    }

    /**手机登录*/
    @RequestMapping("/loginByPhone")
    public Result loginByPhone(@RequestBody UserVo userVo) {
        return userService.loginByPhone(userVo);
    }

    /**修改手机号*/
    @RequestMapping("/upPhone")
    public Result upPhone(@RequestBody UserVo userVo) {
        return userService.upPhone(userVo);
    }

    /**修改登录密码*/
    @RequestMapping("/upPassword")
    public Result upPassword(@RequestBody UserVo userVo){
        return userService.upPassword(userVo);
    }

    /**
     修改支付密码
     */
    @RequestMapping("upPayPassword")
    public Result upPayPassword(@RequestBody UserVo userVo) {
        return userService.upPayPassword(userVo);
    }

    /**注销用户*/
    @RequestMapping("/writeOffUser")
    public Result writeOffUser(@RequestBody UserVo userVo) {
        return userService.writeOffUser(userVo);
    }

    /**
     * 通过用户Id去数据库查询用户信息
     * @param map
     * @return User
     */
    public User selectById(@RequestBody Map<String,String> map) {
        return userService.selectById(map);
    }

    /**
     * 签到
     * @return
     */
    @GetMapping("signIn")
    public Result signIn() {
        return userService.signIn();
    }

    /**
     * 更换皮肤
     * @return
     */
    @GetMapping("replacementSkin")
    public Result ReplacementSkin(@RequestParam  String skinId) {
        return userService.ReplacementSkin(skinId);
    }

    /**
     * 更换头像
     * @return
     */
    @GetMapping("replacementHead")
    public Result ReplacementSkinHead(@RequestParam String imageId) {
        return userService.ReplacementHead(imageId);
    }

    @GetMapping("/setRoleType")
    public Result setRoleType(String type){
        return userService.setRoleType(type);
    }

    @PostMapping("/unbound")
    public Result unbound(@RequestBody UnboundVO unboundVO) {
        String userId = JwtUtils.getUserId();
        String accountId = unboundVO.getAccountId();
        String payPassword = unboundVO.getPayPassword();
        return userService.unbound(userId, accountId, payPassword);
    }


}
