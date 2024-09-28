package com.xboot.jpa.demo.batch;

import com.xboot.jpa.demo.dal.h2.CsvItemRepository;
import jakarta.annotation.Resource;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class CsvItemCleanerTasklet implements Tasklet {

    @Resource
    private CsvItemRepository csvItemRepository;
    @Override
    public RepeatStatus execute(StepContribution contribution,
                                ChunkContext chunkContext) throws Exception {
        csvItemRepository.deleteAll();
        // 返回 FINISHED 状态
        return RepeatStatus.FINISHED;
    }
}
