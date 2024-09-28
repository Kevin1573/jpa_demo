package com.xboot.jpa.demo.common;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * 注释
 *
 * @author xboot
 **/
public  class ClassTypeAdapter implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>> {

    @Override
    public JsonElement serialize(Class<?> src, Type typeOfSrc, JsonSerializationContext context) {
        // 序列化时返回类的全名
        return new JsonPrimitive(src.getName());
    }

    @Override
    public Class<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // 反序列化时根据类名获取对应的 Class 对象
        try {
            return Class.forName(json.getAsString());
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }
}
