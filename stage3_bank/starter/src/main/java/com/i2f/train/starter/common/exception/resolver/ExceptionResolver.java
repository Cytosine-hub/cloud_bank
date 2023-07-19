package com.i2f.train.starter.common.exception.resolver;


import com.i2f.train.starter.common.constants.CommonConstants;
import com.i2f.train.starter.common.exception.BusinessException;
import com.i2f.train.starter.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author cw
 * @date 2022年03月16日 09:44
 */
@RestControllerAdvice
@Slf4j
public class ExceptionResolver {

    @ExceptionHandler(value = Exception.class)
    public Result resolve(Exception e) {
        if (e instanceof BusinessException) {
            log.info("异常信息：${}",e);
            BusinessException businessException = (BusinessException) e;
            return Result.error(businessException.getCode(), businessException.getMessage());
        }
        return Result.error(CommonConstants.Request.INTERNAL_ERROR, CommonConstants.Request.INTERNAL_ERROR_MESSAGE);
    }
}
