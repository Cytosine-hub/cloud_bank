package com.i2f.train.starter.common.interceptor.crypt.impl;

import com.i2f.train.starter.annoation.CryptField;
import com.i2f.train.starter.common.interceptor.crypt.EncryptUtil;
import com.i2f.train.starter.common.util.AESUtil;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @decription 加密
 * @author Wqsh
 * @version 1.0
 * @date 2022/3/29 15:33
 */
@Component
public class AesEncrypt implements EncryptUtil {
    @Override
    public <T> T encrypt(Field[] fields, T parameterObject) throws IllegalAccessException {
        for (Field f : fields) {
            if (f.isAnnotationPresent(CryptField.class)) {
                f.setAccessible(true);
                // Field 表示的字段的值，通过字段得到对象
                Object o = f.get(parameterObject);
                //暂时只实现String类型的加密
                if (o instanceof String) {
                    String value = (String) o;
                    // Field 对象表示的字段设置为指定的新值
                    f.set(parameterObject, AESUtil.jdkAESEncode(value));
                }
            }
        }
        return parameterObject;
    }
}
