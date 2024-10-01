package com.xboot.jpa.demo.controller.api;

import cn.dev33.satoken.annotation.SaIgnore;
import com.google.common.collect.Maps;
import com.xboot.jpa.demo.common.resp.RestResult;
import com.xboot.jpa.demo.util.GsonUtils;
import com.xboot.jpa.demo.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.iter.LineIter;
import org.dromara.hutool.core.io.file.FileAppender;
import org.dromara.hutool.core.io.file.FileWriter;
import org.dromara.hutool.core.lang.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;

/**
 * 注释
 *
 * @author xboot
 **/
@Slf4j
@RestController
@RequestMapping("/api/cache")
public class CacheApiResultToFileController {

    final RestTemplate restTemplate;

    @Autowired
    public CacheApiResultToFileController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SaIgnore
    @RequestMapping("/to_file")
    public RestResult<Object> cacheApiResultToFile() {
        String url = "http://localhost:8080/api/cache/from_file";
        String cacheListFileName = "data/api_result_cache.json";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        String fileName = "data/api_result_tmp1.json";
        FileAppender appender = new FileAppender(new File(cacheListFileName), 16, true);
        try (
                final LineIter lineIter = new LineIter(
                        org.dromara.hutool.core.io.file.FileUtil.getReader(new File(cacheListFileName), Charset.defaultCharset()))
        ) {
            FileWriter writer = new FileWriter(fileName);
            writer.write(Objects.requireNonNull(forEntity.getBody()));
            // 读取 api_result_tmp1.json
            for (String jsonLine : lineIter) {
                Console.log(jsonLine);
                try {
                    RestDto restDto = ObjectUtil.fromJson(jsonLine, RestDto.class);
                    if (restDto.getApiUri().equals(url)) {
                        if (restDto.getApiResult() == null) continue;
                        return RestResult.builder().data(restDto.getApiResult()).build();
                    }
                } catch (Exception e) {
                    log.warn("skip a wow", e);
                }
            }

            // 写入 api_result_cache.json
            RestDto result = new RestDto();
            result.setApiUri(url);
            result.setApiParams(null);
            ApiHeaders apiHeaders = new ApiHeaders();
            apiHeaders.setMethod(HttpMethod.GET.name());
            result.setApiHeaders(apiHeaders);
            result.setApiResult(forEntity.getBody());
            result.setTimeStamp(System.currentTimeMillis());
            appender.append(ObjectUtil.toJsonStr(result));
        } catch (Exception e) {
            log.error("api结果缓存至文件出现错误, {}", e.getMessage());
        } finally {
            appender.flush();
        }
        return RestResult.builder().build();
    }

    @SaIgnore
    @RequestMapping("/from_file")
    public String getCacheApiResultFromFile() {
        Map<String, RestDto> result = Maps.newHashMap();
        result.put("data", new RestDto());
        return GsonUtils.toJson(result);
    }
}
