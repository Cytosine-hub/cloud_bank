package com.i2f.train.starter.model.DTO;

import com.i2f.train.starter.annoation.CryptField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-04-07 09:23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户手机号
     */
    private String phone;

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

    private String token;

    private String email;

}
