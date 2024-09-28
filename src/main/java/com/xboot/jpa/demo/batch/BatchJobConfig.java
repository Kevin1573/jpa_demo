package com.xboot.jpa.demo.batch;

import com.xboot.jpa.demo.dal.dataobject.CsvItem;
import com.xboot.jpa.demo.dal.h2.CsvItemRepository;
import com.xboot.jpa.demo.util.GsonUtils;
import jakarta.annotation.Resource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.validation.BindException;

@Configuration
public class BatchJobConfig {
    private final PlatformTransactionManager transactionManager;
    private final JobRepository jobRepository;
    private final CsvItemCleanerTasklet csvItemCleanerTasklet;
    @Autowired
    public BatchJobConfig(PlatformTransactionManager transactionManager, JobRepository jobRepository, CsvItemCleanerTasklet csvItemCleanerTasklet) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
        this.csvItemCleanerTasklet = csvItemCleanerTasklet;
    }

    @Bean
    public ItemReader<CsvItem> sampleReader() {
        return new FlatFileItemReaderBuilder<CsvItem>()
                .name("sampleReader")
                .resource(new ClassPathResource("sample.txt"))
                .delimited()
                .names("field1", "field2")
                .linesToSkip(1)
                .fieldSetMapper(new CustomFieldSetMapper()) // 添加这一行
//                .targetType(CsvItem.class)  // 配置了targetType, 这里就不需要fieldSetMapper了
                .build();
    }

    @Bean
    public ItemProcessor<CsvItem, CsvItem> sampleProcessor() {
        return item -> {
            System.out.println("Processing: " + GsonUtils.toJson(item));
            return item;  // 示例处理逻辑
        };
    }

    @Resource
    private CsvItemRepository csvItemRepository;

    @Bean
    public ItemWriter<CsvItem> sampleWriter() {
        return items -> items.forEach(it -> {
            csvItemRepository.save(it);
            System.err.println("Writing: " + GsonUtils.toJson(it));
        });
    }

    @Resource
    CsvItemSkipListener csvSkipListener;


    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<CsvItem, CsvItem>chunk(10, transactionManager)
                .reader(sampleReader())
                .processor(sampleProcessor())
                .writer(sampleWriter())
                .faultTolerant()
//                .skip(BindException.class)
                .skip(FlatFileParseException.class)
                .skipLimit(100)
                .listener(csvSkipListener)
//                .listener(new CustomStepExecutionListener())
                .build();
    }

    @Bean
    public Step step0() {
        return new StepBuilder("step0", jobRepository)
                .tasklet(csvItemCleanerTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step recoverTaskletStep() {
        return new StepBuilder("recoverTaskletStep", jobRepository)
                .tasklet(new CsvItemRecoverTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Job importUserJob(Step step0, Step step1, Step recoverTaskletStep) {
        return new JobBuilder("importUserJob", jobRepository)
                //.incrementer(new RunIdIncrementer())
                .start(step0)
                .next(step1)
                .next(recoverTaskletStep)
                .build();
    }

}
