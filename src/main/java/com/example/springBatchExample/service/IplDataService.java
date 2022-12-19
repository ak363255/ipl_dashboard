package com.example.springBatchExample.service;

import com.example.springBatchExample.data.ResponseModel;
import com.example.springBatchExample.model.Match;
import com.example.springBatchExample.model.Team;
import com.example.springBatchExample.repository.IplDataRepository;
import com.example.springBatchExample.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IplDataService {

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    IplDataRepository iplDataRepository;
    public ResponseModel findAllMatchPlayedByATeam(String team,Integer page){
        try{
            if(team == null || team.trim().length()==0){
                return new ResponseModel(null,"Please Enter Valid Team name");
            }else{
                int pageReq = page == null ? 0: page;
               Page<List<Match>> data = iplDataRepository.getAllMatchesPlayedByATeam(team, PageRequest.of(pageReq, com.example.springBatchExample.data.PageRequest.PAGE_SIZE));

               com.example.springBatchExample.data.PageRequest pageRequest = new com.example.springBatchExample.data.PageRequest(
                       data.get().collect(Collectors.toList()), data.hasNext(),page
                );
                return new ResponseModel(
                        pageRequest,
                        "success"
                );
            }
        }catch (Exception e){
            return new ResponseModel(null,"Something went wrong");
        }
    }
    public ResponseModel findAllTeamInIpl(){
        try{
            List<Team> data = teamRepository.findAll();
            if(data == null || data.size()==0){
                return new ResponseModel(data,"Something went wrong");
            }else{
                return new ResponseModel(data,"success");
            }
        }catch (Exception e){
            return new ResponseModel(null,"Something went wrong");
        }
    }
}
