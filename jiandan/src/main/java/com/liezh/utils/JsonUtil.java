package com.liezh.utils;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2018/2/17.
 */
public class JsonUtil {
    private static Gson gson = new Gson();

    public static <T> String toJson(T obj) {
        if (obj == null) {
            return null;
        }
        return gson.toJson(obj);
    }

    public static <T> T toBean(String jsonStr, Class<T> beanClass) {
        if (jsonStr == null) {
            return null;
        }
        return gson.fromJson(jsonStr, beanClass);
    }
}
