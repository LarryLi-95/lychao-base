package com.lycaho.basecommon.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


/**
 * @author lychao
 * @Description:
 * @date 2019/4/4 14:42
 * @since 0.1.0
 */
public class JsonUtil {

    private JsonUtil() {
    }

    private static Gson gs = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private static Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    /**
     * 将对象转为json字符串
     *
     * @param obj
     * @return json字符串
     */
    public static String toJson(Object obj) {
        return toJson(obj, false);
    }

    /**
     * 将对象转为json字符串
     *
     * @param obj
     * @return json字符串
     */
    public static String toJson(Object obj, boolean disableHtmlEscaping) {
        if (disableHtmlEscaping) {
            return gson.toJson(obj);
        }
        return gs.toJson(obj);
    }

    /**
     * 将json字符串转为对象
     *
     * @param json
     * @param classOfT 要转换的类型
     * @return json字符串转换后的对象
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gs.fromJson(json, classOfT);
    }

    /**
     * 将json转为JsonElement
     *
     * @param json
     * @return
     */
    public static JsonObject toJsonObject(String json) {
        return fromJson(json, JsonObject.class);
    }

}

