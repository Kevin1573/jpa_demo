package com.xboot.jpa.demo.common;

import java.io.Serial;
import java.io.Serializable;

/**
 * 注释
 *
 * @author xboot
 **/
public class BaseVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String timestamp = System.currentTimeMillis() + "";
}
