package com.i2f.train.starter.common.util;

import com.i2f.train.starter.common.model.RsaKeyPair;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: third_bird
 * @author: Cytosine
 * @create: 2022-04-02 16:57
 **/
public class RSAUtil {
    private static Cipher cipher;

    static {
        try {
            cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成密钥对
     *
     * @return
     */
    public static RsaKeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            // 密钥位数
            keyPairGen.initialize(2048);
            // 密钥对
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 公钥
            PublicKey publicKey = keyPair.getPublic();
            // 私钥
            PrivateKey privateKey = keyPair.getPrivate();
            //得到公钥字符串
            String publicKeyString = getKeyString(publicKey);
            //得到私钥字符串
            String privateKeyString = getKeyString(privateKey);
            //将生成的密钥对返回
            return RsaKeyPair.builder()
                    .keyId(publicKeyString + System.currentTimeMillis())
                    .publicKey(publicKeyString)
                    .privateKey(privateKeyString)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据字符串得到公钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 根据字符串得到私钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 得到密钥字符串（经过base64编码）
     *
     * @return
     */
    public static String getKeyString(Key key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        String s = (new BASE64Encoder()).encode(keyBytes);
        return s;
    }

    /**
     * 使用公钥对明文进行加密，返回BASE64编码的字符串
     *
     * @param publicKey
     * @param plainText
     * @return
     */
    public static String encrypt(PublicKey publicKey, String plainText) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] enBytes = cipher.doFinal(plainText.getBytes());
            return (new BASE64Encoder()).encode(enBytes);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用keystore对明文进行加密
     *
     * @param publicKeystore 公钥文件路径
     * @param plainText      明文
     * @return
     */
    public static String fileEncrypt(String publicKeystore, String plainText) {
        try {
            FileReader fr = new FileReader(publicKeystore);
            BufferedReader br = new BufferedReader(fr);
            String publicKeyString = "";
            String str;
            while ((str = br.readLine()) != null) {
                publicKeyString += str;
            }
            br.close();
            fr.close();
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKeyString));
            byte[] enBytes = cipher.doFinal(plainText.getBytes());
            return (new BASE64Encoder()).encode(enBytes);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用公钥对明文进行加密
     *
     * @param publicKey 公钥
     * @param plainText 明文
     * @return
     */
    public static String encrypt(String publicKey, String plainText) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
            byte[] enBytes = cipher.doFinal(plainText.getBytes());
            return (new BASE64Encoder()).encode(enBytes);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用私钥对明文密文进行解密
     *
     * @param privateKey
     * @param enStr
     * @return
     */
    public static String decrypt(PrivateKey privateKey, String enStr) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] deBytes = cipher.doFinal((new BASE64Decoder()).decodeBuffer(enStr));
            return new String(deBytes);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用私钥对密文进行解密
     *
     * @param privateKey 私钥
     * @param enStr      密文
     * @return
     */
    public static String decrypt(String privateKey, String enStr) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
            byte[] deBytes = cipher.doFinal((new BASE64Decoder()).decodeBuffer(enStr));
            return new String(deBytes);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用keystore对密文进行解密
     *
     * @param privateKeystore 私钥路径
     * @param enStr           密文
     * @return
     */
    public static String fileDecrypt(String privateKeystore, String enStr) {
        try {
            FileReader fr = new FileReader(privateKeystore);
            BufferedReader br = new BufferedReader(fr);
            String privateKeyString = "";
            String str;
            while ((str = br.readLine()) != null) {
                privateKeyString += str;
            }
            br.close();
            fr.close();
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKeyString));
            byte[] deBytes = cipher.doFinal((new BASE64Decoder()).decodeBuffer(enStr));
            return new String(deBytes);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对加密内容分成两段进行加密
     *
     * @param publicKey
     * @param content
     * @return
     */
    public static List<String> encryptToStringList(String publicKey, String content) {
        List<String> encodedList = new ArrayList<>();
        //将内容分成两部分加密
        int length = content.length();
        int num = 10;
        //分割后的内容存在List中
        List<String> contentList = new ArrayList<>();
//        for (int i = 0; i < length; i++) {
//            if (num * i > length) {
//                contentList.add(content.substring(num * (i - 1), length));
//            }
//            contentList.add(content.substring(num * (i - 1), num * i));
//        }
        contentList.add(content.substring(0, length / 2));
        contentList.add(content.substring(length / 2, length));
        for (String s : contentList) {
            encodedList.add(encrypt(publicKey, s));
        }
        return encodedList;
    }

    /**
     * 对分段加密的内容进行解密
     *
     * @param privateKey
     * @param contentList
     * @return
     */
    public static String decodeListContent(String privateKey, List<String> contentList) {
        StringBuilder builder = new StringBuilder();
        for (String s : contentList) {
            builder.append(decrypt(privateKey, s));
        }
        return builder.toString();
    }

//    public static void main(String[] args) {
//        Map<String, String> keyMap = generateKeyPair();
//        String pubKey =
//                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA9caMV4jOxHQuQoQDkR0lXrIHcEUa/dPM\r\nBBhwi4kBVyI8SaQtRIQxFjD2TIsS7ugPQko1+ZAsQRDhZV/nUqZ4O2ppQMyEJ0951nTAKv2cwcRN\r\nymdrj3dfr7w7tDb1hEhgMBiC/zDfNaBe8+u0G1r97AMwIkj4Y3uCkEi4zXdlHRydagYa/4ll49v6\r\nhT8AG2kaEkk+OMmA6iiaA7Y2DxHGMO04+c6g1fNsMqAM/Ap9VlG3FZxdrSTVeqdTyQc5hz9HUEiK\r\nw0FBi8LRMHQv5zG/wRkJi9IvAn+k+sfq5Pd9I5u7A/Tl3YOuvhF0iZnNnwPI4O9aw6PGywZayhrA\r\nTVkyBQIDAQAB";
//        String priKey =
//                "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQD1xoxXiM7EdC5ChAORHSVesgdw\r\nRRr908wEGHCLiQFXIjxJpC1EhDEWMPZMixLu6A9CSjX5kCxBEOFlX+dSpng7amlAzIQnT3nWdMAq\r\n/ZzBxE3KZ2uPd1+vvDu0NvWESGAwGIL/MN81oF7z67QbWv3sAzAiSPhje4KQSLjNd2UdHJ1qBhr/\r\niWXj2/qFPwAbaRoSST44yYDqKJoDtjYPEcYw7Tj5zqDV82wyoAz8Cn1WUbcVnF2tJNV6p1PJBzmH\r\nP0dQSIrDQUGLwtEwdC/nMb/BGQmL0i8Cf6T6x+rk930jm7sD9OXdg66+EXSJmc2fA8jg71rDo8bL\r\nBlrKGsBNWTIFAgMBAAECggEBAMmjw7BtRXZX+zwHIwYDa41tcjzh9WsaQN97TZqtJLRgGiYw1BLK\r\ngZs7QFc8IqRvL1WrnOzLJAOd3c5WCGr+uNXGT+Qh5qp5wfP4hVDAEcPHUisk/lIOFisPu8v26ydE\r\nWeYbth238ukEUeK9Fl77IChp1SvLwj2cL1pAz52CvypJnCNAuzrXc66U1ZlhJc+WK48tHPheI8CB\r\nyGqqCPXjVrTcpCLd3+bbw55u8X1CIywLcwPuCvYWqAK2XNOSnDjb3U8fur2gnw1SyY3uA53Hlfdz\r\nbKOFFZmqHXXKbvty+VmqhHOsGEM/R4fwg4UavYZTjcar8aHPQiy6bgYHXnKnZgECgYEA/IT7ZBgm\r\nlF+NZNL4qMWqKMtmL7R3WTe/fgKjyuZgfLB5xn9AtmY/iQuugnck3Net8bNWj53ehDTNwmscYT8O\r\nyBQKmlfb1qnm9H/nTtHA6W1Gr+RfBLmIJH0M2gx06JbOJw+QSdZGWgGeJHjUHnCXzWYdhil6JNw0\r\nFOju2HXJ0HECgYEA+SnFNU5eEHM9ie9qHHHpxz0YP4MWVbcu+vWYZoMsQaeD0il9fGk/gwqrOmWR\r\n8S1TN1APkhZGhFgtv3vvQ6PVIPzMf+CerpPENrrf6Dn907Quget4OcsexsRPqygbo7AEeVazF+YQ\r\n1EOEDkIRghQFRfjtECWas5ZkwJob2T59BNUCgYEAg9JX+bSxzxrikROhQ1mMrqcfvR+Ufjqf+IQg\r\nRTdmfzERIRIlEhLmcABtJSZVVAQzhc19DpP3KUT3dNq9gfaNHN15e4EJ/lHLgy+R7I+LCVBGamPp\r\nRmokLAgrwVhnX1P6YmG5jRISv8HAcIGzGgDk/hCksPjODpf1iEThCpbSt8ECgYEA8uTVavHKoR62\r\njxDl0HT8AU59pOyViVnaYquotcaKDjbPOsFXo0C2uXhdE8CQVRxqUzGo/DDa7YlIj7KxvK6pxk6q\r\nzL6b8nP6g6AJRsnBt++epuh1e2GXzwzw3VlsGFYvBlqOnG2bWlG3Gm8twjDiMyRHdcYy2X9zGI8j\r\nNtTD60ECgYEAtKO0yjKUJLawIvwvoJwDlztJ7+1M2PdbN6Jeva1ww5zk2oB+Lz/PBSDf7z8xSIb6\r\n1fF4IGzA/rFZYjgYHXMe+zYs7fsDKD2gfh0xwRrjJuQ7oUrGbmD2B+uojJhZVfbCMbuuTZHKoQ7Y\r\nltDTNrVKz999H/TcTeCwioJcTaVHIiQ=";
//
//        List<String> strings = new ArrayList<>();
//
//        List<String> strings1 = encryptToStringList(pubKey, priKey);
//
//        String encodeS = "Pr02WEcVz/5yC14BJposyqbyKGpZtyj7F3nIgdqZc7I+1opDgCLKEiM09Ts3hEufo4sOb0y8ZBYKoMqJMNEc/gegWNoHzGT77F2C8PkT83lyMvjsH228a9e7EZy1x0QdWcXZO/BWTfSyRG+x6mko0sCW4e+V+KW0OKCWmdvQrobpUREGbgrOsgfY8nyqWzzb2Dhq5GGk4pHy5dIH9AW7qn+AE047S+vWux7ug3pa+wrmhC3at8gwtAXulQ1J/Y8yf40+3SeuP69kDYLOXjtmtdil02CwNwR+OWtXTOsW8z+P7FfmqxzqZJJv2xLUVkJBF8NhrCX0u83zjzlT5tqd7A==";
//        String encodeS2 = "lcFm97ecqmwhdoyPEyVymp7VioQb4XD63e8jWil2oEma9mme7oPtfeBOxeCwUerJol0GIjp0gidejGSgANsHQQEIplHgqjYjPyfYAHY0h3e/UMLLobebiDEmE/xK/0mpdbz7G3yXIY3qQhd2ZFftRiL+RdxKCcf82bqlEr94EJPjtefuCxjZB1E0PkRAquNLyFP5OhITCwM0YL+3JcX8NDgq9rbQI9FuaW5C+ymURN+mhgfH1MMzq8htOP3n9xoNzofy7cc+KWh1AngFB56DkGRqzRCHqI1a6+K/xARpYIkOB32npw6XRFK45smCjcJ+b7ps9eYuY/LOIDJainamdA==";
//        strings.add(encodeS);
//        strings.add(encodeS2);
//
//        String s = decodeListContent(priKey, strings);
//        System.out.println(s);
//    }

}
