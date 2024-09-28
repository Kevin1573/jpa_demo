package com.xboot.jpa.demo.batch;

import com.xboot.jpa.demo.dal.dataobject.CsvItem;
import com.xboot.jpa.demo.util.GsonUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 注释
 *
 * @author xboot
 **/
@Configuration
public class BatchRecoverJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final RecoverItemReader recoverItemReader;

    public BatchRecoverJobConfig(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager,
                                 RecoverItemReader recoverItemReader) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.recoverItemReader = recoverItemReader;
    }

    @Bean
    public ItemWriter<CsvItem> recoverInputWriter() {
        return items -> items.forEach(it -> System.err.println("Recover Writing: " + GsonUtils.toJson(it)));
    }

    @Bean
    public Step recoverStep() {
        return new StepBuilder("recoverStep", jobRepository)
                .<CsvItem, CsvItem>chunk(10, transactionManager)
                .reader(recoverItemReader)
                .writer(recoverInputWriter())
                .build();
    }

    @Bean
    public Job recoverJob(Step recoverStep) {
        return new JobBuilder("recoverJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(recoverStep)
                .build();
    }
}
