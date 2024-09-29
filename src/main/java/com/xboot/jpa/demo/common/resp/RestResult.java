package com.xboot.jpa.demo.common.resp;

import lombok.Getter;

import java.util.Map;

/**
 * 注释
 *
 * @author xboot
 **/
@Getter
public final class RestResult<T> {

    private final Integer code;
    private final String message;
    private final T data;

    // 私有构造方法，防止外部直接new
    private RestResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> RestResultBuilder<T> builder() {
        return new RestResultBuilder<>();
    }

    public static RestResult<String> getDefault500() {
        return RestResult.<String>builder()
                .code(500)
                .message("Internal Server Error")
                .data("Some Data")
                .build();
    }

    public static RestResult<Map<String, Object>> getDefault500(Map<String, Object> data) {
        return RestResult.<Map<String, Object>>builder()
                .code(500)
                .message("Internal Server Error")
                .data(data)
                .build();
    }

    public static RestResult<String> getDefault400() {
        return RestResult.<String>builder()
                .code(400)
                .message("Not Found")
                .data("Some Data")
                .build();
    }

    // Builder类实现
    public static class RestResultBuilder<T> {
        private Integer code = 200; // 默认值
        private String message = "OK"; // 默认值
        private T data;

        // 链式调用方法
        public RestResultBuilder<T> code(Integer code) {
            this.code = code;
            return this;
        }

        public RestResultBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public RestResultBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public RestResult<T> build() {
            return new RestResult<>(code, message, data);
        }
    }
}

