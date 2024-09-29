package com.xboot.jpa.demo.batch;

import com.xboot.jpa.demo.dal.dataobject.BatchErrorLog;
import com.xboot.jpa.demo.dal.dataobject.CsvItem;
import com.xboot.jpa.demo.dal.h2.BatchLogErrorRepository;
import com.xboot.jpa.demo.dal.h2.CsvItemRepository;
import com.xboot.jpa.demo.dto.CsvItemProps;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * 注释
 *
 * @author xboot
 **/
@Configuration
public class BatchRecoverJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final RecoverItemProcessor recoverItemProcessor;
    private final EntityManagerFactory entityManagerFactory;
    private final CsvItemRepository csvItemRepository;
    private final BatchLogErrorRepository batchLogErrorRepository;

    public BatchRecoverJobConfig(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager,
                                 RecoverItemProcessor recoverItemReader,
                                 EntityManagerFactory entityManagerFactory,
                                 CsvItemRepository csvItemRepository, BatchLogErrorRepository batchLogErrorRepository) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.recoverItemProcessor = recoverItemReader;
        this.entityManagerFactory = entityManagerFactory;
        this.csvItemRepository = csvItemRepository;
        this.batchLogErrorRepository = batchLogErrorRepository;
    }

    @Bean
    public JpaPagingItemReader<BatchErrorLog> batchErrorLogItemReader() {
        return new JpaPagingItemReaderBuilder<BatchErrorLog>()
                .name("batchErrorLogItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT u FROM BatchErrorLog u where u.isHandled = false")
                .pageSize(10) // 每次读取10条记录
                .build();
    }

    @Bean
    public ItemWriter<CsvItemProps> recoverInputWriter() {
        return items -> {
            TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
            try {
                List<CsvItem> csvItems = new ArrayList<>();
                List<Long> refIds = new ArrayList<>();
                items.forEach(item -> {
                    CsvItem csvItem = new CsvItem(item.getField1(), item.getField2());
                    csvItems.add(csvItem);
                    refIds.add(item.getRefId());
                });
                csvItemRepository.saveAll(csvItems);
                transactionManager.commit(status);
                // 在保存成功后执行额外的操作
                batchLogErrorRepository.updateHandleStatus(refIds);
            } catch (Exception e) {
                transactionManager.rollback(status);
                throw e;
            }
        };
    }

    @Bean
    public Step recoverStep() {
        return new StepBuilder("recoverStep", jobRepository)
                .<BatchErrorLog, CsvItemProps>chunk(10, transactionManager)
                .reader(batchErrorLogItemReader())
                .processor(recoverItemProcessor)
                .writer(recoverInputWriter())
                .build();
    }

    @Bean
    public Job recoverJob(Step recoverStep) {
        return new JobBuilder("recoverJob", jobRepository)
                //.incrementer(new RunIdIncrementer())
                .start(recoverStep)
                .build();
    }
}
