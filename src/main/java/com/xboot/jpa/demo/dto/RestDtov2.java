package com.xboot.jpa.demo.dto;

import java.io.Serializable;

// 使用 record 定义类
public record RestDtov2(
        String code,
        String msg,
        String data,
        String apiUri,
        ApiHeadersV2 apiHeaders,
        String apiParams,
        String apiResult,
        Long timeStamp
) implements Serializable {

    // 静态工厂方法
    public static RestDtov2 of(
            String code,
            String msg,
            String data,
            String apiUri,
            ApiHeadersV2 apiHeaders,
            String apiParams,
            String apiResult,
            Long timeStamp
    ) {
        return new RestDtov2(code, msg, data, apiUri, apiHeaders, apiParams, apiResult, timeStamp);
    }

    // 构建器模式
    public static class RestDtoBuilder implements Serializable {
        private String code;
        private String msg;
        private String data;
        private String apiUri;
        private ApiHeadersV2 apiHeaders;
        private String apiParams;
        private String apiResult;
        private Long timeStamp;

        public RestDtoBuilder() {
            // document why this constructor is empty
        }

        public RestDtoBuilder code(final String code) {
            this.code = code;
            return this;
        }

        public RestDtoBuilder msg(final String msg) {
            this.msg = msg;
            return this;
        }

        public RestDtoBuilder data(final String data) {
            this.data = data;
            return this;
        }

        public RestDtoBuilder apiUri(final String apiUri) {
            this.apiUri = apiUri;
            return this;
        }

        public RestDtoBuilder apiHeaders(final ApiHeadersV2 apiHeaders) {
            this.apiHeaders = apiHeaders;
            return this;
        }

        public RestDtoBuilder apiParams(final String apiParams) {
            this.apiParams = apiParams;
            return this;
        }

        public RestDtoBuilder apiResult(final String apiResult) {
            this.apiResult = apiResult;
            return this;
        }

        public RestDtoBuilder timeStamp(final Long timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public RestDtov2 build() {
            return new RestDtov2(code, msg, data, apiUri, apiHeaders, apiParams, apiResult, timeStamp);
        }

        public static RestDtoBuilder builder() {
            return new RestDtoBuilder();
        }
    }
}
