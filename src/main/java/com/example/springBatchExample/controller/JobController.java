package com.example.springBatchExample.controller;

import com.example.springBatchExample.repository.IplDataRepository;
import com.example.springBatchExample.batch.runner.IplDataJobRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/run")
public class JobController {
    private IplDataJobRunner iplDataJobRunner;
    @Autowired
    IplDataRepository iplDataRepository;

    @Autowired
    public JobController(IplDataJobRunner iplDataJobRunner){
        this.iplDataJobRunner = iplDataJobRunner;
    }
    @GetMapping("/job")
    public String runJob(){
              iplDataJobRunner.runJobBatchJob();
              return "Batch started";
    }
}
