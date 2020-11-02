package com.embraces.hive.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * @Author Lijl
 * @ClassName DesEncryptUtil
 * @Description TODO
 * @Date 2020/10/29 11:32
 * @Version 1.0
 */
public class DesEncryptUtil {

    public static final String DES_KEY="@Wx^t)V#";

    /**
     * @Author Lijl
     * @MethodName 解密
     * @Description TODO
     * @Date 14:07 2020/10/29
     * @Version 1.0
     * @param src
     * @return: byte[]
    **/
    public static byte[] decrypt(String src) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(DES_KEY.getBytes());
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        // 现在，获取数据并解密
        // 正式执行解密操作
        byte[] bytes = decryptBASE64(src);
        return cipher.doFinal(bytes);
    }

    /**
     * @Author Lijl
     * @MethodName encrypt
     * @Description 数据加密
     * @Date 14:06 2020/10/29
     * @Version 1.0
     * @param src
     * @return: byte[]
    **/
    public static String encrypt(byte[] src) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(DES_KEY.getBytes());
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        // 现在，获取数据并加密
        // 正式执行加密操作
        byte[] bytes = cipher.doFinal(src);
        //解决乱码
        return encryptBASE64(bytes);
    }


    private static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encode(key);
    }
    private static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    public static void main(String[] args) throws Exception {
        /*byte[] decrypt = decrypt("6OGDoGKvRIqEG+NHVXTcnA==");
        System.out.println(new String(decrypt));*/
        encrypt("17600162685".getBytes());
    }
}
