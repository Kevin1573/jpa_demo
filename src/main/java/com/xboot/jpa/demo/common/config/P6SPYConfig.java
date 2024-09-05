package com.xboot.jpa.demo.common.config;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

import java.util.LinkedHashMap;
import java.util.Map;

public class P6SPYConfig  implements MessageFormattingStrategy {
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        Map<String, Object> message = new LinkedHashMap<>(8);
        String newPrepared = prepared.replace("   ", "").replace("\n", " ");
        message.put("prepared", newPrepared);
        String newSql = sql.replace("   ", "").replace("\n", " ");
        message.put("sql", newSql);
        return JSONObject.toJSONString(message, JSONWriter.Feature.PrettyFormat);
    }
}