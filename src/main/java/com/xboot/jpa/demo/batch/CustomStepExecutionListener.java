package com.xboot.jpa.demo.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecutionListener;

/**
 * 注释
 *
 * @author xboot
 **/
public class CustomStepExecutionListener implements StepExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(CustomStepExecutionListener.class);

    @Override
    public void beforeStep(org.springframework.batch.core.StepExecution stepExecution) {
        logger.info("Step is about to start: {}", stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(org.springframework.batch.core.StepExecution stepExecution) {
        if (stepExecution.getExitStatus() == ExitStatus.COMPLETED) {
            logger.info("Step completed successfully: {}", stepExecution.getStepName());
        } else {
            logger.error("Step failed: {} {}", stepExecution.getStepName(), stepExecution.getFailureExceptions());
        }
        System.err.println(stepExecution.getFailureExceptions());
        return stepExecution.getExitStatus();
    }
}
