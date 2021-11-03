package com.lycaho.basecommon.utils;

import java.util.UUID;

public class GenerateUtils {
    public static String generateToken(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    /***
     * 根据位数生成随机数
     * @param num
     * @return
     */
    public static String generateRandomNum(int num){
        StringBuffer randomNum = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int value = (int)(Math.random() * 10);
            randomNum.append(value);
        }
        return randomNum.toString();
    }

}
