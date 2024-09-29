package com.xboot.jpa.demo.controller;

import com.google.common.collect.Maps;
import com.xboot.jpa.demo.common.resp.RestResult;
import com.xboot.jpa.demo.util.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.xboot.jpa.demo.common.resp.RestResult.getDefault400;
import static com.xboot.jpa.demo.common.resp.RestResult.getDefault500;

/**
 * 注释
 *
 * @author xboot
 **/
@Slf4j
@RestController
@RequestMapping("retry")
public class RetryController {
    private final RestTemplate restTemplate;
    private static final String RANDOM_EXECUTE = "http://localhost:8080/random/execute";

    @Autowired
    public RetryController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("againUtilSuccess")
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public RestResult<?> retryAgainUtilSuccess() throws Exception {
        try {
            ResponseEntity<String> exchange = restTemplate.exchange(RANDOM_EXECUTE, HttpMethod.GET, new HttpEntity<>(Maps.newHashMap()), String.class);
            if (exchange.getStatusCode().is2xxSuccessful()) {
                String body = exchange.getBody();
                if (body == null) {
                    return getDefault500();
                } else {
                    log.info("body:{}", body);
                }
                Map<String, Object> bodyMap = GsonUtils.toMap(body);

                if (areEqual((Double) bodyMap.get("code"), 500)) {
                    return getDefault500(bodyMap);
                } else if (areEqual((Double) bodyMap.get("code"), 200)) {
                    return RestResult.<Map<String, Object>>builder()
                            .data(bodyMap)
                            .build();
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return getDefault400();
    }

    public boolean areEqual(double a, double b) {
        return Double.compare(a, b) == 0;
    }

    @RequestMapping("notFound")
    public RestResult<String> notFound() {

        return RestResult.<String>builder()
                .code(404)
                .message("Not Found")
                .data("Some Data")
                .build();
    }

    @RequestMapping("fail")
    public RestResult<String> fail() {

        return getDefault500();
    }

    @RequestMapping("success")
    public RestResult<String> success() {

        return RestResult.<String>builder()
                .code(200)
                .message("OK")
                .data("Some Data")
                .build();
    }
}
