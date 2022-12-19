package com.example.springBatchExample.batch.listerner;

import com.example.springBatchExample.model.Team;
import com.example.springBatchExample.repository.IplDataRepository;
import com.example.springBatchExample.repository.TeamRepository;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IplDataJobListener extends JobExecutionListenerSupport {

    @Autowired
    IplDataRepository iplDataRepository;

    @Autowired
    TeamRepository teamRepository;

    @Override
    public void afterJob(JobExecution jobExecution) {
        super.afterJob(jobExecution);
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            System.out.println("After Job");
            Map<String, Team> teamData = new HashMap<>();
            List<Object[]> data = iplDataRepository.distnictTeam1();
            System.out.println(" data size "+data.size());
            for(Object item[]:data){
                String teamName = (String) item[0];
                int totalMatches = Integer.parseInt(item[1].toString());
                Team t1 = new Team();
                t1.setTeamName(teamName);
                t1.setTeamMatches(totalMatches);
                teamData.put(teamName,t1);
            }

            List<Object[]>data1 = iplDataRepository.distnictTeam2();
            for(Object item[]:data1){
                String teamName = (String) item[0];
                int totalMatches = Integer.parseInt(item[1].toString());
                Team team = teamData.get(teamName);
                team.setTeamMatches(team.getTeamMatches() + totalMatches);
                teamData.put(teamName,team);
            }

            teamData
                    .values()
                    .parallelStream()
                    .forEach(team -> {
                          List<Object[]> wins = iplDataRepository.totalWinsByATeam(team.getTeamName());
                          for(Object item[]:wins){
                              int totalWin = Integer.parseInt(item[0].toString());
                              team.setTotalWins(totalWin);
                          }
                          teamRepository.save(team);

                    });

        }
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        super.beforeJob(jobExecution);
        teamRepository.deleteAll();
        iplDataRepository.deleteAll();


    }
}
