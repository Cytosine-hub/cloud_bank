package com.i2f.train.starter.common.interceptor.crypt.impl;


import com.i2f.train.starter.annoation.CryptField;
import com.i2f.train.starter.common.interceptor.crypt.DecryptUtil;
import com.i2f.train.starter.common.util.AESUtil;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @decription
 * @author Wqsh
 * @version 1.0
 * @date 2022/3/29 15:33
 */
@Component
public class AesDecrypt implements DecryptUtil {
    @Override
    public <T> T decrypt(T result) throws IllegalAccessException {
        Class<?> aClass = result.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
            for (Field f : declaredFields){
                if (f.isAnnotationPresent(CryptField.class)){
                    f.setAccessible(true);
                    //值
                    Object o = f.get(result);
                    if (o instanceof String){
                        String value = (String)o;
                        //解密 将指定对象变量上此 Field 对象表示的字段设置为指定的新值
                        f.set(result, AESUtil.jdkAESDecode(value));
                    }
                }
            }

        return result;
    }
}
