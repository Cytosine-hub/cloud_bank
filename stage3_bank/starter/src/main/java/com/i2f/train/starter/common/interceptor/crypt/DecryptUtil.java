package com.i2f.train.starter.common.interceptor.crypt;

/**
 * @decription 解密核心
 * @author Wqsh
 * @version 1.0
 * @date 2022/3/29 15:33
 */
public interface DecryptUtil {
    <T> T decrypt(T result) throws IllegalAccessException;
}
