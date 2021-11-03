package com.lycaho.basecommon.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 字符串校验工具
 * @author: lychao
 * @time: 2021/7/12 11:02
 */
public class StringVerifyUtils {

    private StringVerifyUtils() {
    }

    private static Pattern allNumberPattern = Pattern.compile("[0-9]*");
    private static Pattern mailPattern = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
    private static Pattern phonePattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

    /**
     * 校验是否全数字
     * @return
     */
    public static boolean verifyAllNumber(String str){
        Matcher matcher = allNumberPattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 校验邮箱是否准确
     * @return
     */
    public static boolean verifyMail(String str){
        Matcher matcher = mailPattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 校验手机是否准确
     * @return
     */
    public static boolean verifyPhone(String str){
        Matcher matcher = phonePattern.matcher(str);
        return matcher.matches();
    }

}
