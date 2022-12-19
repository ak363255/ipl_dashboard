package com.example.springBatchExample.controller;

import com.example.springBatchExample.data.ResponseModel;
import com.example.springBatchExample.service.IplDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ipl")
public class IplDataController {

    @Autowired
    IplDataService iplDataService;
    @GetMapping("/match")
    public ResponseModel findAllMatchPlayedByATeam(@RequestParam("team")String team, @RequestParam(value = "page",required = false)Integer page){
        return iplDataService.findAllMatchPlayedByATeam(team,page);
    }

    @GetMapping("/all_teams")
    public ResponseModel findAllTeams(){
        return iplDataService.findAllTeamInIpl();
    }
}
