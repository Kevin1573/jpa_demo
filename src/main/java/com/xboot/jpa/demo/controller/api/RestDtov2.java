package com.xboot.jpa.demo.controller.api;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class RestDtov2 implements Serializable {

    private String code;
    private String msg;
    private String data;
    private String apiUri;
    private ApiHeaders apiHeaders;
    private String apiParams;
    private String apiResult;
    private Long timeStamp;

    public RestDtov2() {
    }

    public static class RestDtoBuilder implements Serializable {
        private String code;
        private String msg;
        private String data;
        private String apiUri;
        private ApiHeaders apiHeaders;
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

        public RestDtoBuilder apiHeaders(final ApiHeaders apiHeaders) {
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
            return new RestDtov2(this);
        }

        public static RestDtoBuilder builder() {
            return new RestDtoBuilder();
        }
    }

    private RestDtov2(RestDtoBuilder builder) {
        this.code = builder.code;
        this.msg = builder.msg;
        this.data = builder.data;
        this.apiUri = builder.apiUri;
        this.apiHeaders = builder.apiHeaders;
        this.apiParams = builder.apiParams;
        this.apiResult = builder.apiResult;
        this.timeStamp = builder.timeStamp;
    }

}