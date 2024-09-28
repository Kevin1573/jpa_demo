package com.xboot.jpa.demo.controller;

import com.xboot.jpa.demo.common.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 注释
 *
 * @author xboot
 **/
@Slf4j
@RestController
@RequestMapping("job")
public class BatchController {

    private final JobLauncher jobLauncher;

    private final Job importUserJob;

    @Autowired
    public BatchController(JobLauncher jobLauncher, Job importUserJob) {
        this.jobLauncher = jobLauncher;
        this.importUserJob = importUserJob;
    }

    @RequestMapping("runImport")
    public Result runImportJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(importUserJob, params);
        log.info("JobId : {}", jobExecution.getJobId());
        log.info("Exit status : {}", jobExecution.getExitStatus());
        log.info("JobInstance : {}", jobExecution.getJobInstance());
        log.info("StepExecutions : {}", jobExecution.getStepExecutions());
        log.info("JobParameters : {}", jobExecution.getJobParameters());
        log.info("ExecutionContext : {}", jobExecution.getExecutionContext());
        log.info("JobParameters : {}", jobExecution.getJobParameters());
        // 将上边这些属性封装成个Map返回
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("jobId", jobExecution.getJobId());
        resultMap.put("exitStatus", jobExecution.getExitStatus());
        resultMap.put("jobInstance", jobExecution.getJobInstance());
        resultMap.put("stepExecutions", jobExecution.getStepExecutions());
        resultMap.put("executionContext", jobExecution.getExecutionContext());
        resultMap.put("jobParameters", jobExecution.getJobParameters());
        return Result.ok().data(resultMap);
    }
}
