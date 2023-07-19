package com.i2f.train.starter.annoation;

import java.lang.annotation.*;

/**
 * @decription 字段注解
 * @author Wqsh
 * @version 1.0
 * @date 2022/3/29 15:28
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CryptField {
}
