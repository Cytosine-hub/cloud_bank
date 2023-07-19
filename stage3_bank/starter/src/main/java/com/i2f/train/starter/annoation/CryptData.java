package com.i2f.train.starter.annoation;

import java.lang.annotation.*;

/**
 * @decription 类注解
 * @author Wqsh
 * @version 1.0
 * @date 2022/3/29 15:27
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CryptData {
}
