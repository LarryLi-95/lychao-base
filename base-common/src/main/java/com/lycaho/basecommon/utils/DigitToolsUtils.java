package com.lycaho.basecommon.utils;

import com.alibaba.fastjson.JSONArray;

import java.math.BigDecimal;

public class DigitToolsUtils {
    
    public static void main(String[] args) {
        System.out.println("运算结果"+parseInteger(265351012));
    }

    public static Double parseDouble(Object obj){
        if(obj == null){
            return null;
        }
        return Double.parseDouble(obj.toString());
    }

    public static Integer parseInteger(Object obj) {
        // TODO Auto-generated method stub
        if(obj == null){
            return null;
        }
        return Integer.parseInt(obj.toString());
    }

    public static Long parseLong(Object obj) {
        // TODO Auto-generated method stub
        if(obj == null){
            return null;
        }
        return Long.parseLong(obj.toString());
    }
    
    public static Integer[] jsonArrayToIntegers(JSONArray array){
        if(array == null || array.size()<=0){
            return null;
        }
        Integer[] temps = new Integer[array.size()];
        for(int i=0;i<array.size();i++){
            temps[i] = array.getInteger(i);
        }
        return temps;
    }

    public static String[] jsonArrayToStrings(JSONArray array) {
        // TODO Auto-generated method stub
        if(array == null || array.size()<=0){
            return null;
        }
        String[] temps = new String[array.size()];
        for(int i=0;i<array.size();i++){
            temps[i] = array.getString(i);
        }
        return temps;
    }

    public static BigDecimal parseBigDecimal(Object obj) {
        // TODO Auto-generated method stub
        if(obj == null){
            return null;
        }
        return new BigDecimal(obj.toString());
    }
}
