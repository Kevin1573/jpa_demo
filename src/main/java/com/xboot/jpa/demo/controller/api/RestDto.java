package com.xboot.jpa.demo.controller.api;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class RestDto implements Serializable {

    private String code;
    private String msg;
    private String data;
    private String apiUri;
    private ApiHeaders apiHeaders;
    private String apiParams;
    private String apiResult;
    private Long timeStamp;

    public RestDto() {
        //  document why this constructor is empty
    }

}