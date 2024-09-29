package com.xboot.jpa.demo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xboot.jpa.demo.common.ClassTypeAdapter;
import com.xboot.jpa.demo.common.LocalDateTimeTypeAdapter;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 注释
 *
 * @author xboot
 **/
public class GsonUtils {

    private GsonUtils() {}
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .registerTypeAdapter(Class.class, new ClassTypeAdapter())
            .create();
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static Map<String, Object> toMap(String jsonStr) {
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        // 将JSON字符串转换为Map
        return gson.fromJson(jsonStr, type);
    }
}
