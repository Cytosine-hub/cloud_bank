package com.i2f.train.codeProvider.service;

import com.i2f.train.starter.common.model.Result;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-04-01 09:29
 **/
public interface CodeService {
    /**
     * 获取图形验证码
     * @param response
     * @param id
     */
    void getImageCode(HttpServletResponse response,String id);


    /**
     * 获取手机验证码
     * @param mobilePhone
     * @return
     */
    Result getPhoneCode(String mobilePhone);

    /**
     * 验证手机验证码
     * @param mobilePhone
     * @param Code
     * @return
     */
    Result checkPhoneCode(String mobilePhone, String Code);
}
