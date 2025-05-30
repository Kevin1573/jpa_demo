package com.xboot.jpa.demo.common.resp;

import lombok.Data;

@Data
public class ApiResult<T> {
    private int code;
    private String msg;
    private T data;

    private ApiResult() {
    }

    public static <T> ApiResult<T> ok(T data) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(200);
        apiResult.setData(data);
        return apiResult;
    }
    public static <T> ApiResult<T> error(String msg) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(500);
        apiResult.setMsg(msg);
        return apiResult;
    }
}
