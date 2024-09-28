package com.xboot.jpa.demo.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

/**
 * 注释
 *
 * @author xboot
 **/
@Slf4j
@Component
@Deprecated
public class CsvItemRecoverTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution,
                                ChunkContext chunkContext) throws Exception {
        // 获取当前步骤的上下文
        ExitStatus exitStatus = chunkContext.getStepContext().getStepExecution().getJobExecution().getExitStatus();

        // 获取上一个步骤的上下文
        Collection<StepExecution> stepExecutions = chunkContext.getStepContext().getStepExecution().getJobExecution().getStepExecutions();
        Iterator<StepExecution> iterator = stepExecutions.iterator();
        Optional<StepExecution> previousStepExecution = Optional.empty();
        while(iterator.hasNext()) {
            StepExecution next = iterator.next();
            if (next.getStepName().equals("step1")) {
                previousStepExecution = Optional.of(next);
                break;
            }
        }


        if (previousStepExecution.isPresent()) {
            // 获取上一个步骤的信息
            long readCount = previousStepExecution.get().getReadCount();
            long writeCount = previousStepExecution.get().getWriteCount();
            long skipCount = previousStepExecution.get().getSkipCount();
            int failCount = previousStepExecution.get().getFailureExceptions().size();

            System.out.println("Previous step information:");
            System.out.println("Read count: " + readCount);
            System.out.println("Write count: " + writeCount);
            System.out.println("Skip count: " + skipCount);
            System.out.println("Fail count: " + failCount);
        } else {
            System.out.println("No previous step found.");
        }

        return RepeatStatus.FINISHED;
    }
}
