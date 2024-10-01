package com.xboot.jpa.demo.controller.api;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;

/**
 * 注释
 *
 * @author xboot
 **/
@Data
public class ApiHeaders implements Serializable {
    @Serial
    private static final long serialVersionUID = 7516365689444635537L;
    private String token;
    private String method;
    private String contentType;


}
