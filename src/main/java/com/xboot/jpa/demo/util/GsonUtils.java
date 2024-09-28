package com.xboot.jpa.demo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xboot.jpa.demo.common.ClassTypeAdapter;
import com.xboot.jpa.demo.common.LocalDateTimeTypeAdapter;

import java.time.LocalDateTime;

/**
 * 注释
 *
 * @author xboot
 **/
public class GsonUtils {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .registerTypeAdapter(Class.class, new ClassTypeAdapter())
            .create();
    public static String toJson(Object object) {
        return gson.toJson(object);
    }
}
