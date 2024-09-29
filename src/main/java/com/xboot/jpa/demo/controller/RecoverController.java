package com.xboot.jpa.demo.controller;

import com.xboot.jpa.demo.common.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("recover")
public class RecoverController {

    private final JobLauncher jobLauncher;

    private final Job recoverJob;

    @Autowired
    public RecoverController(JobLauncher jobLauncher, @Qualifier("recoverJob") Job recoverJob) {
        this.jobLauncher = jobLauncher;
        this.recoverJob = recoverJob;
    }

    @RequestMapping("batchJob")
    public Result runImportJob(@RequestParam String jobSeq) throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addString("jobSeq", jobSeq)
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(recoverJob, params);
        log.info("JobId : {}", jobExecution.getJobId());
        log.info("Exit status : {}", jobExecution.getExitStatus());
        log.info("JobInstance : {}", jobExecution.getJobInstance());
        // 将上边这些属性封装成个Map返回
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("jobId", jobExecution.getJobId());
        resultMap.put("exitStatus", jobExecution.getExitStatus());
        resultMap.put("jobInstance", jobExecution.getJobInstance());
        return Result.ok().data(resultMap);
    }
}
