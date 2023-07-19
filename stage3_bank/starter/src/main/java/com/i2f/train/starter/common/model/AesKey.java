package com.i2f.train.starter.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author cw
 * @date 2022年03月15日 15:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AesKey implements Serializable {
    private String key;
    private String keyId;
    private long invalidTime;
}
