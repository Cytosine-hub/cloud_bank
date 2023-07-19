package com.i2f.train.gateway.common.exception.resolver;

import com.i2f.train.starter.common.constants.*;
import com.i2f.train.gateway.common.exception.BusinessException;
import com.i2f.train.starter.common.model.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author cw
 * @date 2022年03月16日 09:44
 */
@RestControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(value = Exception.class)
    public Result resolve(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            return Result.error(businessException.getCode(), businessException.getMessage());
        }
        return Result.error(CommonConstants.Request.INTERNAL_ERROR, CommonConstants.Request.INTERNAL_ERROR_MESSAGE);
    }
}
