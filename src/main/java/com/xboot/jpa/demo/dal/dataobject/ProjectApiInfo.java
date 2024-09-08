package com.xboot.jpa.demo.dal.dataobject;

import com.alibaba.fastjson2.JSONObject;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 注释
 *
 * @author xboot
 **/
@Data
@Entity
@Table(name = "project_api_info")
public class ProjectApiInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "project_id")
    private String projectId;
    @Column(name = "api_name")
    private String apiName;
    @Column(name = "api_url")
    private String apiUrl;
    @Column(name = "api_desc")
    private String apiDesc;
    @Column(name = "api_method")
    private String apiMethod;
    @Column(name = "api_param")
    private String apiParam;
    @Column(name = "api_return")
    private String apiReturn;
    @Column(name = "api_return_desc")
    private String apiReturnDesc;
    @Column(name = "api_remark")
    private String apiRemark;

    public ProjectApiInfo() {}

    public ProjectApiInfo(String url, Object method, Object params, Object paramsType) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("params", params);
        paramsMap.put("paramsType", paramsType);
        this.projectId = String.valueOf(1000);
        this.apiParam = JSONObject.toJSONString(paramsMap);
        this.apiUrl = url;
        this.apiMethod = JSONObject.toJSONString(method);
    }
}
