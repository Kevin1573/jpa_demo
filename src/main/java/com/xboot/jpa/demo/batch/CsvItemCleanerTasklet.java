package com.xboot.jpa.demo.batch;

import com.xboot.jpa.demo.dal.h2.BatchLogErrorRepository;
import com.xboot.jpa.demo.dal.h2.CsvItemRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CsvItemCleanerTasklet implements Tasklet {

    @Resource
    private CsvItemRepository csvItemRepository;
    @Resource
    BatchLogErrorRepository batchLogErrorRepository;
    @Override
    public RepeatStatus execute(StepContribution contribution,
                                ChunkContext chunkContext) throws Exception {
        csvItemRepository.deleteAll();
        batchLogErrorRepository.deleteAll();
        log.error("cleaned all data");
        // 返回 FINISHED 状态
        return RepeatStatus.FINISHED;
    }
}
