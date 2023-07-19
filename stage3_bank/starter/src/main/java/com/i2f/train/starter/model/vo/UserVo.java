package com.i2f.train.starter.model.vo;

import com.i2f.train.starter.annoation.CryptData;
import com.i2f.train.starter.annoation.CryptField;
import com.i2f.train.starter.model.Account;
import com.i2f.train.starter.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: 仁
 * 2022/4/2/ 15:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@CryptData
public class UserVo implements Serializable {
    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 登陆密码
     */
    @CryptField
    private String password;

    /**
     * 用户姓名
     */
    private String name;
    /**
     * 电子账户ID
     */
    private String accountId;

    /**
     * 身份证号
     */
    private String personalId;
    /**
     * 二类卡的支付密码
     */
    @CryptField
    private String payPassword;
    /**
     * 手机验证码
     */
    private String phoneCode;
    /**
     * 图形证码
     */
    private String imageCode;
    /**
     * 新密码
     */
    @CryptField
    private String newPassword;

    /**
     * 图形验证码ID
     */
    private String imageId;

    public User toUser() {
        User user=new User();
        user.setId(id);
        user.setPhone(phone);
        user.setPassword(password);
        user.setAccountId(accountId);
        user.setName(name);
        user.setPersonalId(personalId);
        user.setPayPassword(payPassword);
        return user;
    }

    public Account toAccount() {
        Account account=new Account();
        account.setId(accountId);
        account.setPayPassword(payPassword);
        return account;
    }
}
