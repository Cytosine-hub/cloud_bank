package com.i2f.train.manager.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-03-31 11:32
 **/
@Data
@AllArgsConstructor
public class ManagerVo implements Serializable {
    /**
     * 管理员姓名
     */
    private String name;

    /**
     * 管理员手机号
     */
    private String mobilePhone;

    /**
     * 管理员权限等级
     */
    private String level;

    /**
     * 登陆密码
     */
    private String password;

}
