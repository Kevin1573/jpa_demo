package com.xboot.jpa.demo.util;

import lombok.experimental.UtilityClass;

import java.util.Map;

/**
 * 注释
 *
 * @author xboot
 **/
@UtilityClass
public class MapKit {

    public Map<String, String> of(String key, String val) {
        return Map.of(key, val);
    }
}
