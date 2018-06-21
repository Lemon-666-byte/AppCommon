package com.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 */
public class MD5 {

    /**
     * 加密算法
     *
     * @param hash
     * @return
     */
    private static String toHex(byte[] hash) {
        StringBuilder buf = new StringBuilder(hash.length * 2);
        int i;
        for (i = 0; i < hash.length; i++) {
            if (((int) hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    /**
     * 加密
     *
     * @param toDigest
     * @return
     */
    public static String getMD5(String toDigest) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(toDigest.getBytes());
            byte[] b = messageDigest.digest();
            return toHex(b);
        } catch (NoSuchAlgorithmException e) {
            String message = "没有找到 MD5 算法。";
            throw new RuntimeException(message, e);
        }
    }

}
