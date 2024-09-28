package com.xboot.jpa.demo.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.validation.BindException;

import java.util.Arrays;

@Configuration
public class BatchJobConfig {
    private final PlatformTransactionManager transactionManager;
    private final JobRepository jobRepository;

    @Autowired
    public BatchJobConfig(PlatformTransactionManager transactionManager, JobRepository jobRepository) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
    }

    @Bean
    public ItemReader<String[]> sampleReader() {
        return new FlatFileItemReaderBuilder<String[]>()
                .name("sampleReader")
                .resource(new ClassPathResource("sample.txt"))
                .delimited()
                .names("field1", "field2")
                .fieldSetMapper(new PassThroughFieldSetMapper()) // 添加这一行
                .build();
    }
    @Bean
    public CustomFlatFileItemReader customFlatFileItemReader() {
        return new CustomFlatFileItemReader();
    }
    @Bean
    public CustomFlatFileItemReaderDecorator customFlatFileItemReaderDecorator() {
        FlatFileItemReader<CustomFieldSet> itemReader = customFlatFileItemReader().build();
        return new CustomFlatFileItemReaderDecorator(itemReader);
    }

    @Bean
    public ItemProcessor<String[], String[]> sampleProcessor() {
        return item -> {
            System.out.println("Processing: " + Arrays.toString(item));
            return item;  // 示例处理逻辑
        };
    }

    @Bean
    public ItemWriter<String[]> sampleWriter() {
        return items -> items.forEach(it -> System.out.println("Writing: " + Arrays.toString(it)));
    }


    @Bean
    public Step step1() {
        CustomFlatFileItemReaderDecorator customFlatFileItemReaderDecorator = customFlatFileItemReaderDecorator();
        return new StepBuilder("step1", jobRepository)
                .<String[], String[]>chunk(10, transactionManager)
//                .reader(sampleReader())
                .reader(customFlatFileItemReaderDecorator())
                .processor(sampleProcessor())
                .writer(sampleWriter())
                .faultTolerant()
                .skip(BindException.class)
                .skip(FlatFileParseException.class)
                .skipLimit(100)
                .listener(new CustomStepExecutionListener())
                .build();
    }

    @Bean
    public Job importUserJob(Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }

}
