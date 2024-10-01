package com.xboot.jpa.demo.dto;

import java.io.Serializable;

/**
 * 注释
 *
 * @author xboot
 **/
public record ApiHeadersV2(String token,String method,String contentType) implements Serializable {
}
