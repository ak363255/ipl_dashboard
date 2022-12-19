package com.example.springBatchExample.batch.runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class IplDataJobRunner {
    private JobLauncher jobLauncher;
    private Job iplDataJob;

    @Autowired
    public IplDataJobRunner(  JobLauncher jobLauncher, Job iplDataJob){
        this.iplDataJob = iplDataJob;
        this.jobLauncher = jobLauncher;
    }

    @Async
    public void runJobBatchJob(){
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        runJob(iplDataJob,jobParametersBuilder.toJobParameters());
    }
    public void runJob(Job job, JobParameters jobParameters){
        try{
            JobExecution jobExecution = jobLauncher.run(job,jobParameters);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
