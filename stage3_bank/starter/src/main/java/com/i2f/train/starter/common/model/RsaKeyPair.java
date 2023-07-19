package com.i2f.train.starter.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author cw
 * @date 2022年03月15日 15:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RsaKeyPair implements Serializable {
    private String privateKey;
    private String publicKey;
    private String keyId;
}
