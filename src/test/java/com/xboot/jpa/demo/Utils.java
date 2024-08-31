package com.xboot.jpa.demo;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 注释
 *
 * @author xboot
 **/
public class Utils {

    public static String getByteStrSub(String[] contents, int beginPoint, int maxLen) {
        StringBuilder sb = new StringBuilder();
        for (String content : contents) {
            sb.append(content);
        }
        byte[] buf = new byte[maxLen];
        byte[] bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
        if (maxLen - beginPoint >= 0)
            System.arraycopy(bytes, beginPoint, buf, beginPoint, maxLen - beginPoint);
        return new String(buf, StandardCharsets.UTF_8);
    }
}
