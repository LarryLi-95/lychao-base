package com.lycaho.basecommon.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;

/**
 * MD5加密类（封装jdk自带的md5加密方法）
 *
 * @see
 * @version 1.0
 */
public class MD5Util {

    public static String encrypt(String source) {
        return encodeMd5(source.getBytes());
    }

    private static String encodeMd5(byte[] source) {
        try {
            return encodeHex(MessageDigest.getInstance("MD5").digest(source));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private static String encodeHex(byte[] bytes) {
        StringBuffer buffer = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10)
                buffer.append("0");
            buffer.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buffer.toString();
    }

    public static String md5(String... values) {
        return md5(Arrays.asList(values));
    }

    /** 将输入字符串经过MD5处理后返回 */
    public static String md5(Collection<String> values) {
        MessageDigest messageDigest = getMessageDigest("MD5");
        for (String value : values) {
            if (value != null) messageDigest.update(value.getBytes(Charset.forName("UTF-8")));
        }
        return toHexString(messageDigest.digest());
    }

    public static MessageDigest getMessageDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm.toUpperCase());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 provider not exist!");
        }
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (int i = 0, len = bytes.length, value; i < len; i++) {
            value = bytes[i] & 0xff;
            if (value < 0x10) buf.append('0');
            buf.append(Integer.toHexString(value));
        }
        return buf.toString();
    }
}
