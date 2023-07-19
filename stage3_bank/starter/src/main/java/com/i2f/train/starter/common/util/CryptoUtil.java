package com.i2f.train.starter.common.util;


import com.i2f.train.starter.common.exception.CryptoException;
import com.i2f.train.starter.common.model.AesKey;
import com.i2f.train.starter.common.model.RsaKeyPair;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author cw
 * @date 2022年03月15日 10:04
 */
@Slf4j
public class CryptoUtil {
    private static final BASE64Encoder BASE64_ENCODER = new BASE64Encoder();
    private static final BASE64Decoder BASE64_DECODER = new BASE64Decoder();
    private static final String RSA_ALGORITHM = "RSA";
    private static final String RSA_SIGN_ALGORITHM = "MD5WithRSA";
    private static final String AES_ALGORITHM = "AES";
    private static final String AES_TRANSFORMATION = "AES/ECB/PKCS5Padding";

    public static RsaKeyPair generateRsaKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
            keyPairGenerator.initialize(1024, new SecureRandom());
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            String privateKey = BASE64_ENCODER.encode(keyPair.getPrivate().getEncoded());
            String publicKey = BASE64_ENCODER.encode(keyPair.getPublic().getEncoded());
            return RsaKeyPair.builder().keyId(BASE64_ENCODER.encode((publicKey + System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8))).publicKey(publicKey).privateKey(privateKey).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static AesKey generateAesKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
            keyGenerator.init(128, new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            log.info("keyID:" + BASE64_ENCODER.encode(String.valueOf(System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8)));
            return AesKey.builder().key(BASE64_ENCODER.encode(secretKey.getEncoded())).keyId(BASE64_ENCODER.encode(String.valueOf(System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8))).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String rsaEncrypt(String unEncryptedData, String publicKeyStr) {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(BASE64_DECODER.decodeBuffer(publicKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            int keyLen = publicKey.getModulus().bitLength() / 8;
            String[] data = splitString(unEncryptedData, keyLen - 11);
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : data) {
                stringBuilder.append(bcd2Str(cipher.doFinal(s.getBytes())));
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String bcd2Str(byte[] bytes) {
        char[] tmp = new char[bytes.length * 2];
        char val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            tmp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            tmp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(tmp);
    }

    public static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str;
        for (int i = 0; i < x + z; i++) {
            if (i == x + z - 1 && y != 0) {
                str = string.substring(i * len, i * len + y);
            } else {
                str = string.substring(i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }

    public static String rsaDecrypt(String encryptedData, String privateKeyStr) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(BASE64_DECODER.decodeBuffer(privateKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);


            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            int keyLen = privateKey.getModulus().bitLength() / 8;
            byte[] bytes = encryptedData.getBytes();
            byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
            StringBuilder stringBuilder = new StringBuilder();
            byte[][] arrays = splitArray(bcd, keyLen);
            for (byte[] arr : arrays) {
                stringBuilder.append(new String(cipher.doFinal(arr)));
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static byte[][] splitArray(byte[] data, int len) {
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] arrays = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(data, i * len, arr, 0, y);
            } else {
                System.arraycopy(data, i * len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }

    public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = asc_to_bcd(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }

    public static byte asc_to_bcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9')) {
            bcd = (byte) (asc - '0');
        } else if ((asc >= 'A') && (asc <= 'F')) {
            bcd = (byte) (asc - 'A' + 10);
        } else if ((asc >= 'a') && (asc <= 'f')) {
            bcd = (byte) (asc - 'a' + 10);
        } else {
            bcd = (byte) (asc - 48);
        }
        return bcd;
    }

    private static String getString(String encryptedData, Cipher cipher, BigInteger modulus) throws IllegalBlockSizeException, BadPaddingException {
        int keyLen = modulus.bitLength() / 8;
        String[] data = splitString(encryptedData, keyLen - 11);
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : data) {
            stringBuilder.append(BASE64_ENCODER.encode(cipher.doFinal(s.getBytes())));
        }
        return stringBuilder.toString();
    }

    public static String rsaSign(String data, String privateKeyStr) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(BASE64_DECODER.decodeBuffer(privateKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            Signature signature = Signature.getInstance(RSA_SIGN_ALGORITHM);
            signature.initSign(privateKey);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] signed = signature.sign();
            return BASE64_ENCODER.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean rsaSignVerify(String data, String sign, String publicKeyStr) throws CryptoException {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            byte[] encodedKey = BASE64_DECODER.decodeBuffer(publicKeyStr);
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance(RSA_SIGN_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            return signature.verify(BASE64_DECODER.decodeBuffer(sign));
        } catch (Exception e) {
            e.printStackTrace();
            throw new CryptoException();
        }
    }

    public static String aesEncrypt(String unEncryptedData, String key) {
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec sKeySpec = new SecretKeySpec(keyBytes, AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
            byte[] encrypted = cipher.doFinal(unEncryptedData.getBytes(StandardCharsets.UTF_8));
            return BASE64_ENCODER.encode(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String aesDecrypt(String encryptedData, String key) {
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] encrypted1 = BASE64_DECODER.decodeBuffer(encryptedData);
            return new String(cipher.doFinal(encrypted1), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


//    public static void main(String[] args) {
//        RsaKeyPair rsaKeyPair = CryptoUtil.generateRsaKeyPair();
//        log.info("privateKey:\n{}", rsaKeyPair.getPrivateKey());
//        log.info("publicKey:\n{}", rsaKeyPair.getPublicKey());
//        String s = CryptoUtil.rsaEncrypt(rsaKeyPair.getPublicKey(), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCI57IHGtjUzuzwtb3entUxFVKR7rHLRXCqTrJb\nUU+5CbeYDN4bMA2llVH8H0pD1bNu2hMaEmojVCYEHw2lUYTd/4NthWOdKxC7vWCJEGPklJKAUVLc\nR+8o/r5lQcI8un9yt68J+pmTbhNd11nk6emAUeG8Ed2OpHZokiurTdZe3wIDAQAB");
//        CryptoUtil.rsaDecrypt("516C7CBBEB06A9D29B707583EC062BF2CAF62D81645490D8605304075DFFCCCE2197691683F2352EC8B5D25E337548098138016D70E74C543D0DA2B23FB62EF6CEDD244347B96F0F174C7B72F7FD196A31137BF0C7A67209629AA634846C5BE901796ACC8759F121B81826204471876D75C26DF3313AD71FAB26AE998F135C78", "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIiUx555upVnYmzUNs4Brprl700H\n3JqdvytkSMDgWKMMUDz8/mj7o/gT9MDQkh1FT5Bs4sxNKMJ3TyC3oYYnheqEq1a23MbkoVObCdyL\nBMBtlx+IxWv9vqZ/lu+osyF8t8nPSsmQQSkxM3E7iFdBtVupRc0bJaLSWm7xwFQ49EhXAgMBAAEC\ngYBUZJIf4xmeXZ5cnpgqnLF19uPxlg0C4LwSJMorm7RJMz+VH8JXvj2n+TS/u2C9g2lRVWaiVH7+\nAli7X5wH1/4PbcIKZdlUIqgrB8cDq9CFHhrTQ2xZWPTI+GLrMknXpBQM7k7Rr9lyn3sEmH+RRN8W\n6fZSLvodnlUKhF7C7id/sQJBAM2QqIkehWvotfjhndVgvXTQprqmfLcSDUpa3FCfB+nKYo/XG4dr\nqTiZhLV3LrWWr9wqfLL2lbFeE2+MlR08K28CQQCqF09VeiufphA3P9915aalDNZQr8/klBiDvtMz\nvANIrH9mrHfiY/U76VGK04dotZXwsMDhyZVIxVPRmHAPuV2ZAkBko36var7dPpfgWeygq3T1fLUz\nyhBivDa/gU49Ni50mf24+BHlMaMuL5KWGNxIUJaWTkKwhyJwLGONiCD0qCGHAkAUD9/JozlWAU5U\nVfJTg5zeRt9YcQRCX71sfKuIpJp2jfIQ+DLDvyqDwIdfqoeEMDbZRt/ZQd+bz7qNND+zexexAkEA\nj6yyqq1vARlrP4TN5VMcd8rhSPtP3IzVXnp9Deqc7IA7u3LtzJx3I/+kMdeHXT5xeWQS4hpd7mEx\nosUsidWHpw==");
//        log.info("encryptedPublic:\n{}", s);
//    }

}
