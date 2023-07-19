package com.i2f.train.gateway.service;

import com.i2f.train.starter.common.model.AesKey;
import com.i2f.train.starter.common.model.RsaKeyPair;

import java.util.List;

/**
 * @author cw
 * @date 2022年03月16日 09:31
 */
public interface KeyService {
    RsaKeyPair getRsaPublicKey();

    AesKey getAesKey(String keyId, List<String> publicKey);
}
