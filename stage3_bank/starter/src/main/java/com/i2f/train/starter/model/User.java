package com.i2f.train.starter.model;

import com.i2f.train.starter.annoation.CryptData;
import com.i2f.train.starter.annoation.CryptField;
import com.i2f.train.starter.model.DTO.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 17:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@CryptData
public class User implements Serializable {
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
    * 用户性别
    */
    private String sex;

    /**
    * 账号状态
    */
    private String status;

    /**
    * 账号锁定时间
    */
    private Date lockTime;

    /**
    * 积分
    */
    private Integer integral;

    /**
    * 签到时间
    */
    private Date signTime;

    /**
    * 电子账户ID
    */
    private String accountId;

    /**
    * 身份证号
    */
    private String personalId;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 头像ID
    */
    private String imageId;

    /**
    * 皮肤ID
    */
    private String skinId;

    /**
    * 是否销户
    */
    private String isDeleted;

    /**
    * 备用字段1
    */
    private String remark1;

    /**
    * 备用字段2
    */
    private String remark2;

    /**
    * 备用字段3
    */
    private String remark3;

    /**
    * 邮箱
    */
    private String email;

    /**
     * 二类卡的支付密码
     */
    @CryptField
    private String payPassword;

    public UserDto toDto(){
        return new UserDto(id, phone, name, sex, status, integral, signTime, accountId, personalId, createTime, imageId, skinId,isDeleted,email);}

}