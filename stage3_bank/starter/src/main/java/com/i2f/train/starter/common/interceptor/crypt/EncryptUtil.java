package com.i2f.train.starter.common.interceptor.crypt;

import java.lang.reflect.Field;

/**
 * @decription
 * @author Wqsh
 * @version 1.0
 * @date 2022/3/29 15:33
 */
public interface EncryptUtil {
    <T> T encrypt(Field[] fields, T parameterObject) throws IllegalAccessException;
}
