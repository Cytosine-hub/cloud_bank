package com.i2f.train.starter.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.i2f.train.starter.common.constants.*;

import java.io.Serializable;

/**
 * @author cw
 * @date 2022年02月24日 17:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result<R> implements Serializable {
    private String message;
    private R data;
    private String code;

    public static <T> Result<T> success(T data) {
        ResultBuilder<T> builder = Result.builder();
        return builder.data(data).message(CommonConstants.Request.SUCCESS_MESSAGE).code(CommonConstants.Request.SUCCESS).build();
    }

    public static <T> Result<T> success(T data, String code, String message) {
        ResultBuilder<T> builder = Result.builder();
        return builder.data(data).message(message).code(code).build();
    }

    public static  Result success(String code, String message) {
        ResultBuilder builder = Result.builder();
        return builder.message(message).code(code).build();
    }

    public static <T> Result<T> error(String code, String message) {
        ResultBuilder<T> builder = Result.builder();
        return builder.data(null).message(message).code(code).build();
    }

    public static <T> Result<T> error() {
        ResultBuilder<T> builder = Result.builder();
        return builder.data(null).message(CommonConstants.Request.INTERNAL_ERROR_MESSAGE).code(CommonConstants.Request.INTERNAL_ERROR).build();
    }
}
