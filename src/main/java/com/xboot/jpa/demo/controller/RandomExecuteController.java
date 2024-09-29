package com.xboot.jpa.demo.controller;

import com.xboot.jpa.demo.common.resp.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 注释
 *
 * @author xboot
 **/
@Slf4j
@RestController
@RequestMapping("random")
public class RandomExecuteController {
    private final Map<Long, AtomicLong> counterMap = new ConcurrentHashMap<>(16);

    @RequestMapping("execute")
    @Retryable(retryFor = {Exception.class, IllegalArgumentException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public RestResult<String> execute() {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        int nextInt = current.nextInt();
        Thread currentThread = Thread.currentThread();

        AtomicLong counter = counterMap.computeIfAbsent(currentThread.getId(), k -> new AtomicLong(0));
        if (nextInt % 2 == 0) {
            log.warn("random execute fail, execute number:{}, thread-name:{}, counter:{}", counter.get(),
                    currentThread.getId() + "-" + currentThread.getName(), counter.hashCode());
            long failNumber = counter.incrementAndGet();
            throw new IllegalArgumentException("random execute error, fail num:" + failNumber);
        }
        counter.getAndSet(0);
        return RestResult.<String>builder().data("random execute " + nextInt).build();
    }
}
