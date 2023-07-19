package com.i2f.train.gateway.controller;


import com.i2f.train.starter.common.model.AesKey;
import com.i2f.train.starter.common.model.Result;

import com.i2f.train.gateway.service.KeyService;
import com.i2f.train.starter.common.model.RsaKeyPair;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author cw
 * @date 2022年03月15日 09:42
 */
@RestController
@RequestMapping
@CrossOrigin
public class KeyController {
    @Resource
    KeyService keyService;

    @RequestMapping("getPublicKey")
    public Result<RsaKeyPair> getPublicKey() {
        return Result.success(keyService.getRsaPublicKey());
    }

    @RequestMapping("getAesKey")
    public Result<AesKey> getAesKey(@RequestBody Map<String, Object> map){
        return Result.success(keyService.getAesKey((String) map.get("keyId"), (List<String>) map.get("publicKey")));
    }
}
