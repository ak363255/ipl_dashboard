package com.example.springBatchExample.batch.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
@EnableAsync
public class BatchConfig {
    public JobRepository jobRepository;

    @Autowired
    public BatchConfig( JobRepository jobRepository){
        this.jobRepository  = jobRepository;
    }

    @Bean
    public JobLauncher simpleJobLauncer(){
        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepository);
        return simpleJobLauncher;
    }
}
