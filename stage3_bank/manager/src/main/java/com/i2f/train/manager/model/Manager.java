package com.i2f.train.manager.model;

import com.i2f.train.manager.model.dto.ManagerDto;
import com.i2f.train.manager.model.vo.ManagerVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: 刘志亮
 * @date: 2022/3/30 13:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manager implements Serializable {
    /**
     * 管理员ID
     */
    private String id;

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

    /**
     * 备用字段1
     */
    private String remark1;

    /**
     * 备用字段2
     */
    private String remark2;

    public ManagerVo toVo(){
        return new ManagerVo(name, mobilePhone, level, password);
    }

    public ManagerDto toDto(){
        return new ManagerDto(id, name, mobilePhone, level);}
}