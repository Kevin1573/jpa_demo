package com.xboot.jpa.demo.batch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class BatchRunner implements CommandLineRunner {

    private final JobLauncher jobLauncher;

    private final Job job;

    @Autowired
    public BatchRunner(JobLauncher jobLauncher,
                       @Qualifier("compositeJob") Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }


    @Override
    public void run(String... args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        if (jobExecution.getStatus().equals(BatchStatus.COMPLETED)) {
            System.out.println("Job completed successfully.");
        } else {
            System.out.println("Job failed.");
        }
    }
}
