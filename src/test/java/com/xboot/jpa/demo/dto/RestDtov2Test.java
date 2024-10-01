package com.xboot.jpa.demo.dto;


/**
 * 注释
 *
 * @author xboot
 **/
public class RestDtov2Test {


    public static void main(String[] args) {
        ApiHeadersV2 apiHeaders = new ApiHeadersV2("header1", "header2", "contentType");

        RestDtov2 restDtov2 = RestDtov2.RestDtoBuilder.builder()
                .code("200")
                .msg("OK")
                .data("some data")
                .apiUri("/api/v1/endpoint")
                .apiHeaders(apiHeaders)
                .apiParams("param1=value1&param2=value2")
                .apiResult("result data")
                .timeStamp(System.currentTimeMillis())
                .build();

        System.out.println(restDtov2);
    }

}
