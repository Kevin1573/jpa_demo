package com.xboot.jpa.demo.util;

import com.google.gson.Gson;

/**
 * 注释
 *
 * @author xboot
 **/
public class GsonUtils {

    private static final Gson gson = new Gson();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }
}
