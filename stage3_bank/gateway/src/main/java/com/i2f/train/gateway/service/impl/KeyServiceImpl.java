package com.i2f.train.gateway.service.impl;


import com.i2f.train.starter.common.constants.*;
import com.i2f.train.gateway.common.exception.BusinessException;

import com.i2f.train.gateway.service.KeyService;
import com.i2f.train.starter.common.model.AesKey;
import com.i2f.train.starter.common.model.RsaKeyPair;
import com.i2f.train.starter.common.util.CryptoUtil;
import com.i2f.train.starter.common.util.RSAUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author cw
 * @date 2022年03月16日 09:31
 */
@Service
public class KeyServiceImpl implements KeyService {
    private static final String RSA_KEY_PREFIX = "RSA_";
    private static final String AES_KEY_PREFIX = "AES_";
    private static final int RSA_KEY_SAVE_TIME = 6000000;
    private static final int AES_KEY_SAVE_TIME = 1;
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public RsaKeyPair getRsaPublicKey() {
        RsaKeyPair rsaKeyPair = RSAUtil.generateKeyPair();
        if (rsaKeyPair == null) {
            throw new BusinessException(CommonConstants.Request.INTERNAL_ERROR, "生成秘钥失败");
        }
        redisTemplate.opsForValue().set(RSA_KEY_PREFIX + rsaKeyPair.getKeyId(), rsaKeyPair.getPrivateKey(), RSA_KEY_SAVE_TIME, TimeUnit.SECONDS);
        rsaKeyPair.setPrivateKey("");
        return rsaKeyPair;
    }

    @Override
    public AesKey getAesKey(String keyId, List<String> publicKey){
        if (StringUtils.isBlank(keyId) || publicKey == null) {
            throw new BusinessException(CommonConstants.Request.PARAMETER_ERROR, CommonConstants.Request.PARAMETER_ERROR_MESSAGE);
        }
        //根据keyId获取私钥
        String privateKey = (String) redisTemplate.opsForValue().get(RSA_KEY_PREFIX + keyId);
        if (StringUtils.isBlank(privateKey)) {
            throw new BusinessException(CommonConstants.Request.PARAMETER_ERROR, "秘钥已过期，请重新获取");
        }
        //解密前端的公钥
        String decryptedPublicKey = RSAUtil.decodeListContent(privateKey, publicKey);
        if (StringUtils.isBlank(decryptedPublicKey)) {
            throw new BusinessException(CommonConstants.Request.PARAMETER_ERROR, "秘钥不正确，请重试");
        }
        //生成AES密钥
        AesKey aesKey = CryptoUtil.generateAesKey();
        if (aesKey == null) {
            throw new BusinessException(CommonConstants.Request.INTERNAL_ERROR, "生成秘钥失败");
        }
        //保存AES密钥
        redisTemplate.opsForValue().set(AES_KEY_PREFIX + aesKey.getKeyId(), aesKey.getKey(), AES_KEY_SAVE_TIME, TimeUnit.DAYS);
        aesKey.setInvalidTime(System.currentTimeMillis() + 24 * 60 * 59 * 1000);

        aesKey.setKey(RSAUtil.encrypt(decryptedPublicKey,aesKey.getKey()));
        return aesKey;
    }


}
