package com.example.cloudAndPurchasing.ase;

import android.annotation.SuppressLint;
import android.util.Base64;
import android.util.Log;
import android.util.Xml;
import org.apache.http.util.EncodingUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/4/29 0029.
 */
public class AESUtils {

    // 算法/模式/填充
    private static final String CipherMode = "AES/CBC/PKCS5Padding";
    private static final String DEFAULT_PWD = "123456";

    /**
     * 加密字节数据
     * @param content
     * @param password
     * @return
     */
    @SuppressLint("TrulyRandom")
    public static String encrypt(byte[] content, String password) {
        byte[] result = null;
        try {
            SecretKeySpec key = createKey(password);
            Cipher cipher = Cipher.getInstance(CipherMode);
            byte[] iv =  new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 };
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
            result = cipher.doFinal(content);
            String bn = Base64.encodeToString(result,Base64.DEFAULT);
            return bn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密(结果为16进制字符串)
     * @param content
     * @return
     */
    public static String encrypt(String content) {
        byte[] data = null;
        String en = null;
        try {
            data = content.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        en = encrypt(data, DEFAULT_PWD);
        return en;
    }

    /**
     * 加密(结果为16进制字符串)
     * @param content
     * @param password
     * @return
     */
    public static String encrypt(String content, String password) {
        byte[] data = null;
        String vb = null;
        try {
            data = content.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        vb = encrypt(data, password);
        return vb;
    }


    /**
     * 解密字节数组
     * @param content
     * @param password
     * @return
     */
    public static byte[] decrypt(byte[] content, String password) {
        try {
            SecretKeySpec key = createKey(password);
            Cipher cipher = Cipher.getInstance(CipherMode);
            byte[] iv =  new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 };
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     * @param content
     * @return
     */
    public static String enectyol(String content){
        return encrypt(content);
    }
    /**
     * 解密
     * @param content
     * @return
     */
    public static String decrypt(String content) {
        return decrypt(content, DEFAULT_PWD);
    }

    /**
     * 解密16进制的字符串为字符串
     * @param content
     * @param password
     * @return
     */
    public static String decrypt(String content, String password) {
        byte[] data = null;
        try {
            data = Base64.decode(content,0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = decrypt(data, password);
        if (data == null)
            return null;
        String result = null;
        try {
            result = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 字节数组转成16进制字符串
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) { // 一个字节的数，
        StringBuffer sb = new StringBuffer(b.length * 2);
        String tmp = "";
        for (int n = 0; n < b.length; n++) {
            // 整数转成十六进制表示
            tmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString().toUpperCase(); // 转成大写
    }

    /**
     * 将hex字符串转换成字节数组
     * @param inputString
     * @return
     */
    private static byte[] hex2byte(String inputString) {
        if (inputString == null || inputString.length() < 2) {
            return new byte[0];
        }
        inputString = inputString.toLowerCase();
        int l = inputString.length() / 2;
        byte[] result = new byte[l];
        for (int i = 0; i < l; ++i) {
            String tmp = inputString.substring(2 * i, 2 * i + 2);
            result[i] = (byte) (Integer.parseInt(tmp, 16) & 0xFF);
        }
        return result;
    }

    /**
     * 创建密钥
     * @param password
     * @return
     */
    private static SecretKeySpec createKey(String password) {
        byte[] data = null;
        if (password == null) {
            password = "";
        }

        try {
            data = password.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            data = md.digest(data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return new SecretKeySpec(data, "AES");
    }
}
