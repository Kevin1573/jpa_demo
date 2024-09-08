package com.xboot.jpa.demo.controller.req;

import lombok.Data;

import java.io.Serializable;

/**
 * 注释
 *
 * @author xboot
 **/
@Data
public class StudentReq implements Serializable {
    private String name;
    private String state;
}
