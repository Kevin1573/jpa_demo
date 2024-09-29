package com.xboot.jpa.demo.common.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        restTemplate.getMessageConverters()
                .stream()
                .filter(MappingJackson2HttpMessageConverter.class::isInstance)
                .map(MappingJackson2HttpMessageConverter.class::cast)
                .findFirst()
                .map(MappingJackson2HttpMessageConverter::getObjectMapper)
                .ifPresent(objectMapper -> {
                    // 去掉默认的时间戳格式
                    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                    // 设置为东八区
                    objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                    // 序列化时，日期的统一格式
                    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                    // 忽略大小写
                    objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
                });

        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(150000); // ms
        factory.setConnectTimeout(150000); // ms
        return factory;
    }

}

