package com.xboot.jpa.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;

/**
 * 主要用于对象对象的高效序列和反序列化
 *
 * @author xboot
 **/
@UtilityClass
public class ObjectUtil {
    ObjectMapper mapper = new ObjectMapper();

    public String serialize(Object object) throws IOException {
        String serializeFileName = "user.json";
        mapper.writeValue(new File(serializeFileName), object);
        return serializeFileName;
    }

    public String toJsonStr(Object object) throws IOException {
        return mapper.writeValueAsString(object);
    }

    public <T> T deserialize(String serializeFileName, Class<T> clazz) throws IOException {
        // 从文件中读取JSON数据并反序列化为User对象
        return mapper.readValue(new File(serializeFileName), clazz);
    }

    public <T> T fromJson(String jsonLine, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(jsonLine, clazz);
    }
}
