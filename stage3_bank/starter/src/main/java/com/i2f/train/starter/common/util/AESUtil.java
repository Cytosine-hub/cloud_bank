package com.i2f.train.starter.common.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author Cytosine
 * AES加密工具类
 */
public class AESUtil {

    private static final String password ="disanzuchongchongchong";


    public static String jdkAESEncode(String str){

        try {
            Key key = getSecretKey(password);
            //加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,key);
            byte [] result = cipher.doFinal(str.getBytes());
            String string = Base64.encode(result);
            return string;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static String jdkAESDecode(String str){
        try {
            Key key = getSecretKey(password);
            //解密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,key);
            byte [] bytesContent = Base64.decode(str);
            byte [] result = cipher.doFinal(bytesContent);
            return new String(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成密钥 避免Linux和windows加密结果不同
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static SecretKeySpec getSecretKey(final String key) throws NoSuchAlgorithmException {
        // 返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key.getBytes());
        kg.init(128, secureRandom);
        SecretKey secretKey = kg.generateKey();
        // 转换为AES专用密钥
        return new SecretKeySpec(secretKey.getEncoded(), "AES");
    }

    public static void main(String[] args) {
        String s = jdkAESEncode("100000.00");
        System.out.println(s);
    }
}
