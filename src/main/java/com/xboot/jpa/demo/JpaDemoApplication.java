package com.xboot.jpa.demo;

import cn.dev33.satoken.SaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class JpaDemoApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(JpaDemoApplication.class, args);
        LOGGER.info("启动成功：sa-token配置如下：({})", SaManager.getConfig());
    }

}
