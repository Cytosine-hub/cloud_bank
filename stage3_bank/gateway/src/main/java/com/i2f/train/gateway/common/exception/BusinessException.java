package com.i2f.train.gateway.common.exception;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author cw
 * @date 2022年03月15日 13:24
 */
@AllArgsConstructor
@Data
public class BusinessException extends RuntimeException {
    private String code;
    private String msg;
}
