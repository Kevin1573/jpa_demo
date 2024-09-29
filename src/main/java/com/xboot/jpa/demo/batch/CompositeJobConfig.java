package com.xboot.jpa.demo.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 注释
 *
 * @author xboot
 **/
@Slf4j
@Configuration
public class CompositeJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public CompositeJobConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Step checkFirstJobStatusStep() {
        return new StepBuilder("checkFirstJobStatusStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("checkFirstJobStatusStep {}", contribution.getStepExecution().getStatus());
                    if (contribution.getExitStatus().getExitCode().equals("COMPLETED")) {
                        // 如果第一个 Job 成功，则启动第二个 Job
                        return RepeatStatus.FINISHED;
                    } else {
                        // 如果第一个 Job 失败，则跳过第二个 Job
                        return RepeatStatus.FINISHED;
                    }
                }, transactionManager)
                .build();
    }

    @Bean
    public Flow splitFlow(Step step0, Step step1) {
        return new FlowBuilder<SimpleFlow>("splitFlow")
                .start(step0)
                .next(step1)
                .build();
    }

    @Bean
    public Job compositeJob(Flow splitFlow, Step recoverStep) {
        return new JobBuilder("compositeJob", jobRepository)
                .start(splitFlow)
                .next(checkFirstJobStatusStep())
                .on("COMPLETED").to(recoverStep)
                .end()
                .build();
    }
}
